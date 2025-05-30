package ee.bcs.dosesyncback.service.study;

import ee.bcs.dosesyncback.controller.study.dto.NewStudy;
import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.controller.study.dto.StudyResult;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeRepository;
import ee.bcs.dosesyncback.persistence.machine.Machine;
import ee.bcs.dosesyncback.persistence.machine.MachineRepository;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFillRepository;
import ee.bcs.dosesyncback.persistence.study.*;
import ee.bcs.dosesyncback.service.patientinjection.PatientInjectionService;
import ee.bcs.dosesyncback.util.SimulationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMapper studyMapper;
    private final CalculationProfileRepository calculationProfileRepository;
    private final IsotopeRepository isotopeRepository;
    private final MachineRepository machineRepository;
    private final DailyStudyRepository dailyStudyRepository;
    private final MachineFillRepository machineFillRepository;
    private final PatientInjectionService patientInjectionService;

    public List<StudyInfo> getAllStudies() {
        List<Study> studies = studyRepository.findAll();
        return studyMapper.toStudyInfos(studies);
    }

    @Transactional
    public Integer addStudy(NewStudy newStudy) {
        Study savedStudy = createAndSaveNewStudy(newStudy);
        return savedStudy.getId();
    }

    private Study createAndSaveNewStudy(NewStudy newStudy) {
        Study study = studyMapper.toStudy(newStudy);
        addStudyIsotope(newStudy, study);
        addStudyMachine(newStudy, study);
        study.setStatus(StudyStatus.PENDING.getCode());
        return studyRepository.save(study);
    }

    private void addStudyMachine(NewStudy newStudy, Study study) {
        Integer machineId = newStudy.getMachineId();
        Machine machine = machineRepository.getReferenceById(machineId);
        study.setMachine(machine);
    }

    private void addStudyIsotope(NewStudy newStudy, Study study) {
        Integer isotopeId = newStudy.getIsotopeId();
        Isotope isotope = isotopeRepository.getReferenceById(isotopeId);
        study.setIsotope(isotope);
    }

    public BigDecimal calculateStudiesMachineRinseVolume(Integer studyId) {
        double bestRinseVolume = 0.0;
        double smallestDiff = Double.MAX_VALUE;
        SimulationResult bestResult = null;

        for (int i = 100; i <= 400; i++) {
            double candidate = i / 100.0;
            SimulationResult result = simulateMachineFill(studyId, candidate);

            double diff = Math.abs(result.getLastRemainingVolume() - 3.0);
            if (diff < smallestDiff) {
                smallestDiff = diff;
                bestRinseVolume = candidate;
                bestResult = result;
            }
        }

        // Step 2: Adjust until first injected volume >= 0.2
        while (true) {
            assert bestResult != null;
            if (!(bestResult.getFirstInjectedVolume() < 0.2 && bestRinseVolume < 2.0)) break;
            bestRinseVolume += 0.01;
            bestResult = simulateMachineFill(studyId, bestRinseVolume);
        }

        if (bestRinseVolume > 2.0) {
            bestRinseVolume = 2.0;
            bestResult = simulateMachineFill(studyId, bestRinseVolume);
        }

        machineFillRepository.saveAll(bestResult.getSimulatedFills());
        Study study = studyRepository.getReferenceById(studyId);
        BigDecimal rinseVolumeBigDecimal = BigDecimal.valueOf(bestRinseVolume);
        BigDecimal rinseVolume = fitToPrecisionScale(rinseVolumeBigDecimal);
        study.setCalculationMachineRinseVolume(rinseVolume);
        studyRepository.save(study);
        return rinseVolume;
    }

    private BigDecimal fitToPrecisionScale(BigDecimal value) {
        if (value == null) return null;

        // Round to 2 decimals
        value = value.setScale(2, RoundingMode.HALF_UP);

        // Clamp to max allowed value for precision 8, scale 2: 999999.99
        BigDecimal maxAllowed = new BigDecimal("999999.99");
        if (value.abs().compareTo(maxAllowed) > 0) {
            value = value.signum() < 0 ? maxAllowed.negate() : maxAllowed;
        }

        return value;
    }
    private SimulationResult simulateMachineFill(Integer studyId, double candidateRinseVolume) {
        Study study = studyRepository.getReferenceById(studyId);
        BigDecimal halfLifeHours = study.getIsotope().getHalfLifeHr();
        BigDecimal vialVolume = BigDecimal.valueOf(candidateRinseVolume);

        CalculationProfile profile = calculationProfileRepository.findCalculationProfilesBy(studyId).getLast();
        LocalTime calibrationTime = profile.getCalibrationTime();
        BigDecimal calibratedActivity = profile.getCalibratedActivity();
        BigDecimal fillVolume = BigDecimal.valueOf(profile.getFillVolume());

        List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesByStudyOrderedByInjectionTime(studyId);
        List<MachineFill> simulatedFills = new ArrayList<>();

        BigDecimal remainingVolume = BigDecimal.ZERO;
        BigDecimal firstInjectedVolume = BigDecimal.ZERO;

        DailyStudy previous = null;

        BigDecimal vialActivityBeforeInjection;
        BigDecimal vialActivityAfterInjection = null;

        for (DailyStudy current : dailyStudies) {
            Injection injection = current.getInjection();
            BigDecimal currentInjectedActivity = injection.getInjectedActivity();

            MachineFill machineFill = new MachineFill();
            machineFill.setInjection(injection);


            if (previous == null) {
                BigDecimal decayFactor = calculateDecayFactorBetweenTimes(calibrationTime, injection.getInjectedTime(), halfLifeHours.doubleValue());
                vialActivityBeforeInjection = calibratedActivity.multiply(decayFactor);
                BigDecimal totalVialVolume = vialVolume.add(fillVolume);
                BigDecimal injectedVolume = currentInjectedActivity.divide(vialActivityBeforeInjection, 6, RoundingMode.HALF_UP).multiply(totalVialVolume);
                remainingVolume = totalVialVolume.subtract(injectedVolume);

                machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);
                vialActivityAfterInjection = vialActivityBeforeInjection.subtract(currentInjectedActivity);
                machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);
                // Prevent division by near-zero or negative activity
                if (vialActivityBeforeInjection.compareTo(BigDecimal.valueOf(0.0001)) < 0) {
                    break; // or continue; or add a fallback injectedVolume
                }
                machineFill.setInjectedVolume(injectedVolume);
                machineFill.setRemainingVolume(remainingVolume);

                firstInjectedVolume = injectedVolume;
            } else {
                MachineFill previousFill = simulatedFills.get(simulatedFills.size() - 1);
                Injection previousInjection = previous.getInjection();

                BigDecimal decayFactor = calculateDecayFactorBetweenTimes(previousInjection.getInjectedTime(), injection.getInjectedTime(), halfLifeHours.doubleValue());
                vialActivityBeforeInjection = previousFill.getVialActivityAfterInjection().multiply(decayFactor);

                BigDecimal injectedVolume = currentInjectedActivity.divide(vialActivityBeforeInjection, 6, RoundingMode.HALF_UP)
                        .multiply(previousFill.getRemainingVolume());
                remainingVolume = previousFill.getRemainingVolume().subtract(injectedVolume);

                machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);
                vialActivityAfterInjection = vialActivityBeforeInjection.subtract(currentInjectedActivity);
                machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);
                if (vialActivityBeforeInjection.compareTo(BigDecimal.valueOf(0.0001)) < 0) {
                    break; // or continue; or add a fallback injectedVolume
                }
                machineFill.setInjectedVolume(injectedVolume);
                machineFill.setRemainingVolume(remainingVolume);
            }

            simulatedFills.add(machineFill);
            previous = current;
        }

        study.setCalculationMachineRinseActivity(vialActivityAfterInjection);

        return new SimulationResult(
                firstInjectedVolume.doubleValue(),
                remainingVolume.doubleValue(),
                simulatedFills
        );
    }

    private BigDecimal calculateDecayFactorBetweenTimes(LocalTime from, LocalTime to, double halfLifeHours) {
        long minutesBetween = ChronoUnit.MINUTES.between(from, to);
        double deltaTInDays = minutesBetween / 1440.0;
        double decayFactor = Math.pow(2, -deltaTInDays / (halfLifeHours / 24.0));
        return BigDecimal.valueOf(decayFactor);
    }


    public BigDecimal getStudiesLastMachineRinseActivity(Integer studyId) {
        List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesBy(studyId);
        DailyStudy lastDailyStudy = dailyStudies.getFirst();
        return lastDailyStudy.getMachineFill().getVialActivityAfterInjection();
    }

    @Transactional
    public void addStudyInformation(Integer studyId) {
        Study study = studyRepository.findById(studyId).orElseThrow(() -> new PrimaryKeyNotFoundException("studyId", studyId));
        findAndSetStudyInformation(studyId, study);
        studyRepository.save(study);
    }

    private void findAndSetStudyInformation(Integer studyId, Study study) {
        addStudyTotalActivity(studyId, study);
        List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesBy(studyId);
        addStudyFirstInjectionTime(dailyStudies, study);
        addStudyLastInjectionTimeAndRinseActivity(dailyStudies, study);
        study.setStatus(StudyStatus.COMPLETED.getCode());
        study.setNrPatients(dailyStudies.size());
    }

    private void addStudyLastInjectionTimeAndRinseActivity(List<DailyStudy> dailyStudies, Study study) {
        DailyStudy lastDailyStudy = dailyStudies.getFirst();
        BigDecimal vialActivityAfterInjection = lastDailyStudy.getMachineFill().getVialActivityAfterInjection();
        study.setCalculationMachineRinseActivity(vialActivityAfterInjection);
        LocalTime lastInjectedTime = lastDailyStudy.getInjection().getInjectedTime();
        study.setEndTime(lastInjectedTime);
    }

    private void addStudyFirstInjectionTime(List<DailyStudy> dailyStudies, Study study) {
        DailyStudy firstDailyStudy = dailyStudies.getLast();
        LocalTime firstInjectedTime = firstDailyStudy.getInjection().getInjectedTime();
        study.setStartTime(firstInjectedTime);
    }

    private void addStudyTotalActivity(Integer studyId, Study study) {
        List<CalculationProfile> calculationProfiles = calculationProfileRepository.findCalculationProfilesBy(studyId);
        CalculationProfile calculationProfilesFirst = calculationProfiles.getFirst();
        study.setTotalActivity(calculationProfilesFirst.getCalibratedActivity());
    }

    @Transactional
    public void updatePendingStudyInformation(Integer studyId, NewStudy newStudy) {
        Study study = studyRepository.getReferenceById(studyId);
        studyMapper.updateStudy(study, newStudy);

        Machine machine = machineRepository.getReferenceById(newStudy.getMachineId());
        study.setMachine(machine);

        Isotope isotope = isotopeRepository.getReferenceById(newStudy.getIsotopeId());
        study.setIsotope(isotope);

        studyRepository.save(study);
    }

    @Transactional
    public void removePendingStudy(Integer studyId) {
        Study study = studyRepository.getReferenceById(studyId);
        studyRepository.delete(study);
    }

    public StudyInfo getStudy(Integer studyId) {
        Study study = studyRepository.getReferenceById(studyId);
        return studyMapper.toStudyInfo(study);
    }

    public StudyResult getStudyResult(Integer studyId) {
        Study study = studyRepository.getReferenceById(studyId);
        StudyResult studyResult = new StudyResult();
        studyResult.setCalculationMachineRinseVolume(fitToPrecisionScale(study.getCalculationMachineRinseVolume()));
        studyResult.setCalculationMachineRinseActivity(fitToPrecisionScale(study.getCalculationMachineRinseActivity()));
        return studyResult;
    }
}
