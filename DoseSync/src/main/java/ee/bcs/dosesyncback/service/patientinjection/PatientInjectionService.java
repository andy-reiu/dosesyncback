package ee.bcs.dosesyncback.service.patientinjection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.EditPatientInjectionRequest;
import ee.bcs.dosesyncback.controller.patientinjection.dto.NewPatientInjectionRequest;
import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSetting;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingRepository;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.injection.InjectionRepository;
import ee.bcs.dosesyncback.persistence.injection.PatientInjectionMapper;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeRepository;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    private final IsotopeRepository isotopeRepository;
    private final CalculationSettingRepository calculationSettingRepository;

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
    public void addPatientInjection(Integer isotopeId, NewPatientInjectionRequest newPatientInjectionRequest) {

        Integer studyId = newPatientInjectionRequest.getStudyId();
        Study study = studyRepository.getReferenceById(studyId);
        Injection injection = createAndSaveInjection(newPatientInjectionRequest);
        Patient patient = createAndSavePatient(newPatientInjectionRequest);

        double halfLifeDays = getStudiesIsotopeHalfLifeDays(isotopeId);
        BigDecimal vialVolume = Optional.ofNullable(study.getCalculationMachineRinseVolume())
                .orElse(BigDecimal.ZERO);

        BigDecimal currentInjectedActivity = injection.getInjectedActivity();

        // Calculate the machine fills for the syringe
        MachineFill machineFill = new MachineFill();

        if (isFirstDailyStudy(studyId)) {

            CalculationProfile calculationProfile = getLastCalculationProfile(studyId);
            BigDecimal decayFactorBigDecimal = calculateDecayFactorBetweenTimes(calculationProfile.getCalibrationTime(),
                    injection, halfLifeDays);

            //    private BigDecimal vialActivityBeforeInjection;
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

            BigDecimal decayFactorBigDecimal = calculateDecayFactorBetweenTimes(previousInjection.getInjectedTime(), injection, halfLifeDays);
            MachineFill perviousMachineFill = previousDailyStudy.getMachineFill();

            //     calculate the vialActivityBeforeInjection;
            BigDecimal previousVialActivityAfterInjection = perviousMachineFill.getVialActivityAfterInjection();
            BigDecimal vialActivityBeforeInjection = previousVialActivityAfterInjection.multiply(decayFactorBigDecimal);
            machineFill.setVialActivityBeforeInjection(vialActivityBeforeInjection);

            //     calculate the vialActivityAfterInjection;
            BigDecimal vialActivityAfterInjection = vialActivityBeforeInjection.subtract(currentInjectedActivity);
            machineFill.setVialActivityAfterInjection(vialActivityAfterInjection);

            //     calculate the vialActivityAfterInjection;
            BigDecimal injectedVolume = currentInjectedActivity.divide(vialActivityBeforeInjection, 6, RoundingMode.HALF_UP).multiply(perviousMachineFill.getRemainingVolume());
            machineFill.setInjectedVolume(injectedVolume);

            //    calculate the remainingVolume;
            BigDecimal remainingVolume = perviousMachineFill.getRemainingVolume().subtract(injectedVolume);
            machineFill.setRemainingVolume(remainingVolume);
        }

        machineFill.setInjection(injection);
        machineFillRepository.save(machineFill);

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

    private boolean isFirstDailyStudy(Integer studyId) {
        return !dailyStudyRepository.existsInDailyStudyBy(studyId);
    }

    private BigDecimal calculateDecayFactorBetweenTimes(LocalTime calculationProfile, Injection injection, double halfLifeDays) {
        long minutesBetween = ChronoUnit.MINUTES.between(calculationProfile, injection.getInjectedTime());
        double deltaTDays = minutesBetween / 1440.0; // Convert minutes to days
        double decayFactor = Math.pow(2, -deltaTDays / halfLifeDays);
        return BigDecimal.valueOf(decayFactor);
    }

    private CalculationProfile getLastCalculationProfile(Integer studyId) {
        List<CalculationProfile> calculationProfiles = calculationProfileRepository.findCalculationProfilesBy(studyId);
        return calculationProfiles.getLast();
    }

    private double getStudiesIsotopeHalfLifeDays(Integer isotopeId) {
        Isotope isotope = isotopeRepository.getReferenceById(isotopeId);
        BigDecimal halfLifeDaysBD = isotope.getHalfLifeHr();
        return halfLifeDaysBD.doubleValue();
    }

    private Patient createAndSavePatient(NewPatientInjectionRequest newPatientInjectionRequest) {
        String patientNationalId = newPatientInjectionRequest.getPatientNationalId();
        return patientRepository.findPatientBy(patientNationalId)
                .orElseGet(() -> saveNewPatient(patientNationalId));
    }

    private Patient saveNewPatient(String patientNationalId) {
        Patient newPatient = new Patient();
        newPatient.setPatientNationalId(patientNationalId);
        return patientRepository.save(newPatient);
    }

    private Injection createAndSaveInjection(NewPatientInjectionRequest newPatientInjectionRequest) {
        Injection injection = patientInjectionMapper.toInjection(newPatientInjectionRequest);
        injectionRepository.save(injection);
        return injection;
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
                .orElseGet(() -> saveNewPatient(nationalId));

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

        Study study = studyRepository.getReferenceById(studyId);
        BigDecimal halfLifeDays = study.getIsotope().getHalfLifeHr();
        BigDecimal vialVolume = Optional.ofNullable(study.getCalculationMachineRinseVolume())
                .orElse(BigDecimal.ZERO);

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
                BigDecimal decayFactorBD = calculateDecayFactorBetweenTimes(calculationProfile.getCalibrationTime(), injection, halfLifeDays.doubleValue());
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

                BigDecimal decayFactorBD = calculateDecayFactorBetweenTimes(previousInjection.getInjectedTime(), injection, halfLifeDays.doubleValue());
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

            previousDailyStudy = dailyStudy;
            machineFillRepository.save(machineFill);
        }
    }

    public NewPatientInjectionRequest getStudyPatientInjectionTemplate(Integer studyId) {
        NewPatientInjectionRequest newPatientInjectionRequest = new NewPatientInjectionRequest();
        newPatientInjectionRequest.setStudyId(studyId);
        newPatientInjectionRequest.setPatientNationalId("");
        getNewAcc(newPatientInjectionRequest);
        List<CalculationSetting> calculationSettings = calculationSettingRepository.findAll();
        CalculationSetting calculationSetting = calculationSettings.getFirst();
        getNewInjectedActivity(calculationSetting, newPatientInjectionRequest);
        getNewInjectionTime(studyId, calculationSetting, newPatientInjectionRequest);
        return newPatientInjectionRequest;
    }

    private void getNewInjectedActivity(CalculationSetting calculationSetting, NewPatientInjectionRequest newPatientInjectionRequest) {
        BigDecimal defaultPatientWeight = getBigDecimal(calculationSetting, newPatientInjectionRequest);
        BigDecimal defaultActivityPerKg = calculationSetting.getActivityPerKg();
        newPatientInjectionRequest.setInjectionMbqKg(defaultActivityPerKg);
        newPatientInjectionRequest.setInjectedActivity(defaultActivityPerKg.multiply(defaultPatientWeight));
    }

    private void getNewInjectionTime(Integer studyId, CalculationSetting calculationSetting, NewPatientInjectionRequest newPatientInjectionRequest) {
        LocalTime startTime;
        if (isFirstDailyStudy(studyId)) {
            startTime = getInjectionTimeFromCalculationProfile(studyId);
        } else {
            startTime = getInjectionTimeFromPreviousInjection(studyId, calculationSetting);
        }
        newPatientInjectionRequest.setInjectedTime(startTime);
    }

    private LocalTime getInjectionTimeFromPreviousInjection(Integer studyId, CalculationSetting calculationSetting) {
        LocalTime startTime;
        LocalTime injectionInterval = calculationSetting.getInjectionInterval();
        List<DailyStudy> dailyStudies = dailyStudyRepository.findDailyStudiesBy(studyId);
        DailyStudy previousDailyStudy = dailyStudies.getFirst();
        Injection injection = previousDailyStudy.getInjection();
        startTime = injection.getInjectedTime();
        startTime = startTime.plusMinutes(injectionInterval.getMinute());
        return startTime;
    }

    private LocalTime getInjectionTimeFromCalculationProfile(Integer studyId) {
        LocalTime startTime;
        CalculationProfile calculationProfile = getLastCalculationProfile(studyId);
        startTime = calculationProfile.getCalibrationTime();
        startTime = startTime.plusHours(2);
        return startTime;
    }

    private BigDecimal getBigDecimal(CalculationSetting calculationSetting, NewPatientInjectionRequest newPatientInjectionRequest) {
        BigDecimal defaultPatientWeight = BigDecimal.valueOf(calculationSetting.getDefaultPatientWeight());
        newPatientInjectionRequest.setInjectionWeight(defaultPatientWeight);
        return defaultPatientWeight;
    }

    private void getNewAcc(NewPatientInjectionRequest newPatientInjectionRequest) {
        String itkACC = getNewStudyAcc("ITKHU");
        newPatientInjectionRequest.setAcc(itkACC);
    }

    public static String giveRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public static String getNewStudyAcc(String accRoot) {
        String randomValueAsString = giveRandomString();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String dateToday = df.format(new Date());
        return accRoot + dateToday + randomValueAsString.toUpperCase();
    }
}
