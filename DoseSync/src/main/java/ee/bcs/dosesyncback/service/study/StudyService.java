package ee.bcs.dosesyncback.service.study;

import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.controller.study.dto.StudyRequest;
import ee.bcs.dosesyncback.controller.study.dto.StudyResult;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingRepository;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeRepository;
import ee.bcs.dosesyncback.persistence.machine.Machine;
import ee.bcs.dosesyncback.persistence.machine.MachineRepository;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFillRepository;
import ee.bcs.dosesyncback.persistence.study.Study;
import ee.bcs.dosesyncback.persistence.study.StudyMapper;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
import ee.bcs.dosesyncback.persistence.study.StudyStatus;
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
    private final CalculationSettingRepository calculationSettingRepository;

    public List<StudyInfo> getAllStudies() {
        List<Study> studies = studyRepository.findStudiesByAsc();
        return studyMapper.toStudyInfos(studies);
    }

    @Transactional
    public Integer addStudy(StudyRequest studyRequest) {
        Study savedStudy = createAndSaveNewStudy(studyRequest);
        return savedStudy.getId();
    }

    public BigDecimal calculateStudiesMachineRinseVolume(Integer studyId) {
        double bestRinseVolume = 0.0;
        double smallestDiff = Double.MAX_VALUE;
        SimulationResult bestResult = null;

        //todo: min ja max võtta andmebaasist.
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

        BigDecimal minVolume = calculationSettingRepository.findById(1).get().getMinVolume();
        double minVolumeDouble = minVolume.doubleValue();

        // Step 2: Adjust until first injected volume >= 0.2
        // todo: add functionality for checking/changing 2.0
        while (true) {
            assert bestResult != null;
            if (!(bestResult.getFirstInjectedVolume() < minVolumeDouble && bestRinseVolume < 2.0)) break;
            bestRinseVolume += 0.01;
            bestResult = simulateMachineFill(studyId, bestRinseVolume);
        }

        if (bestRinseVolume > 2.0) {
            bestRinseVolume = 2.0;
            bestResult = simulateMachineFill(studyId, bestRinseVolume);
        }

        machineFillRepository.saveAll(bestResult.getSimulatedFills());
        BigDecimal rinseVolume = SaveStudiesRinseVolume(studyId, bestRinseVolume);
        return rinseVolume;
    }

    public BigDecimal getStudiesLastMachineRinseActivity(Integer studyId) {
        List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesBy(studyId);
        DailyStudy lastDailyStudy = dailyStudies.getFirst();
        return lastDailyStudy.getMachineFill().getVialActivityAfterInjection();
    }

    @Transactional
    public void addStudyInformation(Integer studyId) {
        Study study = getValidStudy(studyId);
        findAndSetStudyInformation(studyId, study);
        studyRepository.save(study);
    }

    @Transactional
    public void updatePendingStudyInformation(Integer studyId, StudyRequest studyRequest) {
        Study study = getValidStudy(studyId);
        studyMapper.updateStudy(study, studyRequest);
        updateStudyMachine(study, studyRequest);
        updateStudyIsotope(study, studyRequest);
        studyRepository.save(study);
    }

    @Transactional
    public void removeStudy(Integer studyId) {
        removeAllInjectionsAndMachineFills(studyId);
        removeAllCalculationProfiles(studyId);
        studyRepository.deleteById(studyId);
    }

    public StudyInfo getStudy(Integer studyId) {
        Study study = getValidStudy(studyId);
        return studyMapper.toStudyInfo(study);
    }

    public StudyResult getStudyResult(Integer studyId) {
        Study study = getValidStudy(studyId);
        StudyResult studyResult = new StudyResult();
        getStudyResultFromStudy(study, studyResult);
        return studyResult;
    }

    private Study createAndSaveNewStudy(StudyRequest studyRequest) {
        Study study = studyMapper.toStudy(studyRequest);
        addStudyIsotope(study, studyRequest);
        addStudyMachine(study, studyRequest);
        study.setStatus(StudyStatus.PENDING.getCode());
        return studyRepository.save(study);
    }

    private void addStudyMachine(Study study, StudyRequest studyRequest) {
        updateStudyMachine(study, studyRequest);
    }

    private void addStudyIsotope(Study study, StudyRequest studyRequest) {
        Integer isotopeId = studyRequest.getIsotopeId();
        Isotope isotope = getValidIsotope(isotopeId);
        study.setIsotope(isotope);
    }

    private BigDecimal fitDoubleToBigDecimal(BigDecimal value) {
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

    private BigDecimal SaveStudiesRinseVolume(Integer studyId, double bestRinseVolume) {
        Study study = studyRepository.getReferenceById(studyId);
        BigDecimal rinseVolumeBigDecimal = BigDecimal.valueOf(bestRinseVolume);
        BigDecimal rinseVolume = fitDoubleToBigDecimal(rinseVolumeBigDecimal);
        study.setCalculationMachineRinseVolume(rinseVolume);
        studyRepository.save(study);
        return rinseVolume;
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
        double deltaTInHours = minutesBetween / 60.0; // Use hours directly
        double decayFactor = Math.pow(2, -deltaTInHours / halfLifeHours);
        return BigDecimal.valueOf(decayFactor);
    }

    private Study getValidStudy(Integer studyId) {
        return studyRepository.findStudyBy(studyId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("studyId", studyId));
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

    private void updateStudyMachine(Study study, StudyRequest studyRequest) {
        Integer machineId = studyRequest.getMachineId();
        Machine machine = getValidMachine(machineId);
        study.setMachine(machine);
    }

    private void updateStudyIsotope(Study study, StudyRequest studyRequest) {
        Integer isotopeId = studyRequest.getIsotopeId();
        Isotope isotope = getValidIsotope(isotopeId);
        study.setIsotope(isotope);
    }

    private Isotope getValidIsotope(Integer isotopeId) {
        return isotopeRepository.findIsotopeBy(isotopeId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("isotopeId", isotopeId));
    }

    private Machine getValidMachine(Integer machineId) {
        return machineRepository.findMachineBy(machineId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("machineId", machineId));
    }

    private void removeAllCalculationProfiles(Integer studyId) {
        List<CalculationProfile> profiles = calculationProfileRepository.findCalculationProfilesBy(studyId);
        calculationProfileRepository.deleteAll(profiles);
    }

    private void removeAllInjectionsAndMachineFills(Integer studyId) {
        List<DailyStudy> dailyStudies = dailyStudyRepository.getDailyStudiesBy(studyId);
        for (DailyStudy dailyStudy : dailyStudies) {
            Integer injectionId = dailyStudy.getInjection().getId();
            patientInjectionService.removePatientInjection(injectionId); // Reuse existing method
        }
    }

    private void getStudyResultFromStudy(Study study, StudyResult studyResult) {
        BigDecimal calculationMachineRinseVolume = study.getCalculationMachineRinseVolume();
        studyResult.setCalculationMachineRinseVolume(fitDoubleToBigDecimal(calculationMachineRinseVolume));
        BigDecimal calculationMachineRinseActivity = study.getCalculationMachineRinseActivity();
        studyResult.setCalculationMachineRinseActivity(fitDoubleToBigDecimal(calculationMachineRinseActivity));
    }
}
