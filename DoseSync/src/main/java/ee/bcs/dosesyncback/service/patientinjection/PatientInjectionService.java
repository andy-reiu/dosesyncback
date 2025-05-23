package ee.bcs.dosesyncback.service.patientinjection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.NewPatientInjectionRequest;
import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.injection.InjectionRepository;
import ee.bcs.dosesyncback.persistence.injection.PatientInjectionMapper;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import ee.bcs.dosesyncback.persistence.machinefill.MachineFillRepository;
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
    public void newPatientInjection(NewPatientInjectionRequest newPatientInjectionRequest) {

        Integer studyId = newPatientInjectionRequest.getStudyId();
        Injection injection = patientInjectionMapper.toInjection(newPatientInjectionRequest);

        System.out.println("Injection mapped: " + injection);
        injectionRepository.save(injection);

        Patient patient = patientRepository.findPatientBy(newPatientInjectionRequest.getPatientNationalId())
                .orElseGet(() -> {
                    Patient newPatient = new Patient();
                    newPatient.setPatientNationalId(newPatientInjectionRequest.getPatientNationalId());
                    return patientRepository.save(newPatient);
                });

        double halfLifeDays = 0.0762; // F-18 half-life
        BigDecimal vialVolume = BigDecimal.valueOf(2);

        // New machine fill made
        MachineFill machineFill = new MachineFill();
        if (!dailyStudyRepository.existsInDailyStudyBy(studyId)) {
            // TODO: peaks olema ka mitu kalkulatsiooni profiili ja error viskamine
            List<CalculationProfile> calculationProfiles = calculationProfileRepository.findCalculationProfilesBy(studyId);

            // TODO: Just make it work
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
            BigDecimal vialActivityAfterInjection = vialActivityBeforeInjection.subtract(injection.getInjectedActivity());
            machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);

            //     private BigDecimal injectedVolume;
            vialVolume = vialVolume.add(BigDecimal.valueOf(calculationProfile.getFillVolume()));
            BigDecimal injectedVolume = injection.getInjectedActivity().divide(vialVolume, 6, RoundingMode.HALF_UP);
            machineFill.setInjectedVolume(injectedVolume);

            //    private BigDecimal remainingVolume;
            BigDecimal remainingVolume = vialVolume.subtract(injectedVolume);
            machineFill.setRemainingVolume(remainingVolume);

        } else {
            List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesBy(studyId);
            DailyStudy previousDailyStudy = dailyStudies.getLast();
            Injection previousInjection = previousDailyStudy.getInjection();

            long minutesBetween = ChronoUnit.MINUTES.between(previousInjection.getInjectedTime(), injection.getInjectedTime());
            double deltaTDays = minutesBetween / 1440.0; // Convert minutes to days
            double decayFactor = Math.pow(2, -deltaTDays / halfLifeDays);

            //    private BigDecimal vialActivityBeforeInjection;
            BigDecimal decayFactorBigDecimal = BigDecimal.valueOf(decayFactor);
            MachineFill perviousMachineFill = previousDailyStudy.getMachineFill();
            BigDecimal previousVialActivityAfterInjection = perviousMachineFill.getVialActivityAfterInjection();
            BigDecimal vialActivityBeforeInjection = previousVialActivityAfterInjection.multiply(decayFactorBigDecimal);
            machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);

            //     private BigDecimal vialActivityAfterInjection;
            BigDecimal vialActivityAfterInjection = vialActivityBeforeInjection.subtract(injection.getInjectedActivity());
            machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);

            //     private BigDecimal vialActivityAfterInjection;
            BigDecimal injectedVolume = vialActivityAfterInjection.divide(perviousMachineFill.getRemainingVolume(), 6, RoundingMode.HALF_UP);
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
}
