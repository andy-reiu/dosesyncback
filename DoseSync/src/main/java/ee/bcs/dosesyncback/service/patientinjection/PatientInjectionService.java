package ee.bcs.dosesyncback.service.patientinjection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.EditPatientInjectionRequest;
import ee.bcs.dosesyncback.controller.patientinjection.dto.NewPatientInjectionRequest;
import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.injection.InjectionRepository;
import ee.bcs.dosesyncback.persistence.injection.PatientInjectionMapper;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFillRepository;
import ee.bcs.dosesyncback.persistence.machinefillcalculationprofile.MachineFillCalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.patient.Patient;
import ee.bcs.dosesyncback.persistence.patient.PatientRepository;
import ee.bcs.dosesyncback.persistence.study.Study;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
import ee.bcs.dosesyncback.persistence.study.StudyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientInjectionService {

    private final PatientInjectionMapper patientInjectionMapper;
    private final DailyStudyRepository dailyStudyRepository;
    private final PatientRepository patientRepository;
    private final InjectionRepository injectionRepository;
    private final CalculationProfileRepository calculationProfileRepository;
    private final MachineFillRepository machineFillRepository;
    private final StudyRepository studyRepository;
    private final MachineFillCalculationProfileRepository machineFillCalculationProfileRepository;

    public List<PatientInjectionInfo> getAllStudiesPatientInjections(Integer studyId) {
        List<DailyStudy> dailyStudies = dailyStudyRepository.getDailyStudiesBy(studyId);
        List<PatientInjectionInfo> patientInjectionInfos = new ArrayList<>();
        for (DailyStudy dailyStudy : dailyStudies) {
            Injection injection = dailyStudy.getInjection();
            PatientInjectionInfo patientInjectionInfo = patientInjectionMapper.toPatientInjectionInfo(injection);
            Patient patient = dailyStudy.getPatient();
            patientInjectionInfo.setPatientNationalId(patient.getPatientNationalId());
            patientInjectionInfos.add(patientInjectionInfo);
        }
        return patientInjectionInfos;
    }

    @Transactional
    public void addPatientInjection(NewPatientInjectionRequest newPatientInjectionRequest) {

        Integer studyId = newPatientInjectionRequest.getStudyId();
        Injection injection = patientInjectionMapper.toInjection(newPatientInjectionRequest);
        injectionRepository.save(injection);

        Patient patient = patientRepository.findPatientBy(newPatientInjectionRequest.getPatientNationalId())
                .orElseGet(() -> {
                    Patient newPatient = new Patient();
                    newPatient.setPatientNationalId(newPatientInjectionRequest.getPatientNationalId());
                    return patientRepository.save(newPatient);
                });

        double halfLifeDays = 0.0762; // F-18 half-life
        BigDecimal vialVolume = BigDecimal.valueOf(2);

        BigDecimal currentInjectedActivity = injection.getInjectedActivity();

        // New machine fill made
        MachineFill machineFill = new MachineFill();
        if (!dailyStudyRepository.existsInDailyStudyBy(studyId)) {

            List<CalculationProfile> calculationProfiles = calculationProfileRepository.findCalculationProfilesBy(studyId);

            CalculationProfile calculationProfile = calculationProfiles.getLast();

            long minutesBetween = ChronoUnit.MINUTES.between(calculationProfile.getCalibrationTime(), injection.getInjectedTime());
            double deltaTDays = minutesBetween / 1440.0; // Convert minutes to days
            double decayFactor = Math.pow(2, -deltaTDays / halfLifeDays);

            //    private BigDecimal vialActivityBeforeInjection;
            BigDecimal decayFactorBigDecimal = BigDecimal.valueOf(decayFactor);
            BigDecimal calibratedActivity = calculationProfile.getCalibratedActivity();
            BigDecimal vialActivityBeforeInjection = calibratedActivity.multiply(decayFactorBigDecimal);
            machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);

            //     private BigDecimal vialActivityAfterInjection;
            BigDecimal vialActivityAfterInjection = vialActivityBeforeInjection.subtract(currentInjectedActivity);
            machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);

            //     private BigDecimal injectedVolume;
            vialVolume = vialVolume.add(BigDecimal.valueOf(calculationProfile.getFillVolume()));
            BigDecimal injectedVolume = currentInjectedActivity.divide(vialActivityBeforeInjection, 6, RoundingMode.HALF_UP).multiply(vialVolume);
            machineFill.setInjectedVolume(injectedVolume);

            //    private BigDecimal remainingVolume;
            BigDecimal remainingVolume = vialVolume.subtract(injectedVolume);
            machineFill.setRemainingVolume(remainingVolume);

        } else {
            List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesBy(studyId);
            DailyStudy previousDailyStudy = dailyStudies.getFirst();
            Injection previousInjection = previousDailyStudy.getInjection();

            long minutesBetween = ChronoUnit.MINUTES.between(previousInjection.getInjectedTime(), injection.getInjectedTime());
            double deltaTDays = minutesBetween / 1440.0; // Convert minutes to days
            double decayFactor = Math.pow(2, -deltaTDays / halfLifeDays);

            //    private BigDecimal vialActivityBeforeInjection;
            BigDecimal decayFactorBigDecimal = BigDecimal.valueOf(decayFactor);
            MachineFill perviousMachineFill = previousDailyStudy.getMachineFill();
            System.out.println(perviousMachineFill.getVialActivityAfterInjection());
            BigDecimal previousVialActivityAfterInjection = perviousMachineFill.getVialActivityAfterInjection();
            BigDecimal vialActivityBeforeInjection = previousVialActivityAfterInjection.multiply(decayFactorBigDecimal);
            System.out.println(vialActivityBeforeInjection);
            machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);

            //     private BigDecimal vialActivityAfterInjection;
            BigDecimal vialActivityAfterInjection = vialActivityBeforeInjection.subtract(currentInjectedActivity);
            machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);

            //     private BigDecimal vialActivityAfterInjection;
            BigDecimal injectedVolume = currentInjectedActivity.divide(vialActivityBeforeInjection, 6, RoundingMode.HALF_UP).multiply(perviousMachineFill.getRemainingVolume());
            machineFill.setInjectedVolume(injectedVolume);

            //    private BigDecimal remainingVolume;
            BigDecimal remainingVolume = perviousMachineFill.getRemainingVolume().subtract(injectedVolume);
            machineFill.setRemainingVolume(remainingVolume);
        }

        machineFill.setInjection(injection);
        machineFillRepository.save(machineFill);

        Study study = studyRepository.findById(studyId).orElseThrow();

        DailyStudy dailyStudy = new DailyStudy();
        dailyStudy.setStudy(study);
        dailyStudy.setInjection(injection);
        dailyStudy.setMachineFill(machineFill);
        dailyStudy.setPatient(patient);
        dailyStudy.setStatus(StudyStatus.PENDING.getCode());
        dailyStudy.setCreatedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        dailyStudy.setUpdatedAt(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        dailyStudyRepository.save(dailyStudy);
    }

    @Transactional
    public void updatePatientInjection(Integer studyId, EditPatientInjectionRequest editPatientInjectionRequest) {
        Integer injectionId = editPatientInjectionRequest.getInjectionId();

        Injection injection = injectionRepository.getReferenceById(injectionId);
        Injection updateInjection = patientInjectionMapper.toUpdateInjection(editPatientInjectionRequest, injection);
        injectionRepository.save(updateInjection);

        DailyStudy dailyStudy = dailyStudyRepository.findDailyStudyBy(injectionId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("injectionId",injectionId));

        String nationalId = editPatientInjectionRequest.getPatientNationalId();
        Patient patient = patientRepository.findPatientBy(nationalId)
                .orElseGet(() -> {
                    Patient newPatient = new Patient();
                    newPatient.setPatientNationalId(nationalId);
                    return patientRepository.save(newPatient); // Save new patient
                });

        if (!Objects.equals(dailyStudy.getPatient().getId(), patient.getId())) {
            dailyStudy.setPatient(patient);
            dailyStudyRepository.save(dailyStudy);
        }

        recalculateMachineFillsForStudy(studyId);
    }

    @Transactional
    public void removePatientInjection(Integer patientInjectionId) {
        Optional<DailyStudy> dailyStudy = dailyStudyRepository.findDailyStudyBy(patientInjectionId);
        Integer studyId = dailyStudy.get().getStudy().getId();

        dailyStudyRepository.deleteByInjection(patientInjectionId);

        MachineFill machineFill = machineFillRepository.findMachineFillBy(patientInjectionId);

        //Foreign key constraint violations, check if its null or not
        if (machineFill != null) {
            machineFillCalculationProfileRepository.deleteByMachineFillId(machineFill.getId());
            machineFillRepository.delete(machineFill);
        }

        machineFillRepository.deleteByInjection(patientInjectionId);
        injectionRepository.deleteById(patientInjectionId);
        recalculateMachineFillsForStudy(studyId);
    }

    @Transactional
    public void recalculateMachineFillsForStudy(Integer studyId) {


        BigDecimal halfLifeDays = BigDecimal.valueOf(0.0762);
        BigDecimal vialVolume = BigDecimal.valueOf(2);

        DailyStudy previousDailyStudy = null;
        List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesByStudyOrderedByInjectionTime(studyId);

        for (DailyStudy dailyStudy : dailyStudies) {
            Injection injection = dailyStudy.getInjection();
            BigDecimal currentInjectedActivity = injection.getInjectedActivity();

            MachineFill machineFill = dailyStudy.getMachineFill();
            if (machineFill == null) {
                machineFill = new MachineFill();
                machineFill.setInjection(injection);
            }

            if (previousDailyStudy == null) {

                CalculationProfile calculationProfile = calculationProfileRepository.findCalculationProfilesBy(studyId).getLast();
                // First injection recalculation (based on calibration profile)
                long minutesBetween = ChronoUnit.MINUTES.between(calculationProfile.getCalibrationTime(), injection.getInjectedTime());
                double deltaTDays = minutesBetween / 1440.0;
                double decayFactor = Math.pow(2, -deltaTDays / halfLifeDays.doubleValue());

                BigDecimal decayFactorBD = BigDecimal.valueOf(decayFactor);
                BigDecimal calibratedActivity = calculationProfile.getCalibratedActivity();
                BigDecimal vialActivityBeforeInjection = calibratedActivity.multiply(decayFactorBD);

                machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);

                BigDecimal vialActivityAfterInjection = vialActivityBeforeInjection.subtract(currentInjectedActivity);
                machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);

                BigDecimal totalVialVolume = vialVolume.add(BigDecimal.valueOf(calculationProfile.getFillVolume()));
                BigDecimal injectedVolume = currentInjectedActivity.divide(vialActivityBeforeInjection, 6, RoundingMode.HALF_UP).multiply(totalVialVolume);
                machineFill.setInjectedVolume(injectedVolume);

                BigDecimal remainingVolume = totalVialVolume.subtract(injectedVolume);
                machineFill.setRemainingVolume(remainingVolume);

            } else {
                // Subsequent injection recalculation (based on previous machine fill)
                Injection previousInjection = previousDailyStudy.getInjection();
                MachineFill previousMachineFill = previousDailyStudy.getMachineFill();

                long minutesBetween = ChronoUnit.MINUTES.between(previousInjection.getInjectedTime(), injection.getInjectedTime());
                double deltaTDays = minutesBetween / 1440.0;
                double decayFactor = Math.pow(2, -deltaTDays / halfLifeDays.doubleValue());

                BigDecimal decayFactorBD = BigDecimal.valueOf(decayFactor);
                BigDecimal previousVialActivityAfterInjection = previousMachineFill.getVialActivityAfterInjection();
                BigDecimal vialActivityBeforeInjection = previousVialActivityAfterInjection.multiply(decayFactorBD);

                machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);

                BigDecimal vialActivityAfterInjection = vialActivityBeforeInjection.subtract(currentInjectedActivity);
                machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);

                BigDecimal injectedVolume = currentInjectedActivity.divide(vialActivityBeforeInjection, 6, RoundingMode.HALF_UP)
                        .multiply(previousMachineFill.getRemainingVolume());
                machineFill.setInjectedVolume(injectedVolume);

                BigDecimal remainingVolume = previousMachineFill.getRemainingVolume().subtract(injectedVolume);
                machineFill.setRemainingVolume(remainingVolume);
            }

            machineFillRepository.save(machineFill);
            previousDailyStudy = dailyStudy;
        }
    }
}
