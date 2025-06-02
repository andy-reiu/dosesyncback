package ee.bcs.dosesyncback.service.calculationprofile;

import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileInfo;
import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileRequest;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileMapper;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.study.Study;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
import ee.bcs.dosesyncback.service.patientinjection.PatientInjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationProfileService {

    private final CalculationProfileMapper calculationProfileMapper;
    private final CalculationProfileRepository calculationProfileRepository;
    private final StudyRepository studyRepository;
    private final PatientInjectionService patientInjectionService;

    public List<CalculationProfileInfo> getAllStudiesCalculationProfiles(Integer studyId) {
        List<CalculationProfile> calculationProfiles = calculationProfileRepository.findCalculationProfilesBy(studyId);
        return calculationProfileMapper.toCalculationProfileInfos(calculationProfiles);
    }

    @Transactional
    public Integer addCalculationProfile(CalculationProfileRequest calculationProfileRequest) {
        CalculationProfile calculationProfile = calculationProfileMapper.toCalculationProfile(calculationProfileRequest);
        Isotope isotope = getStudyIsotopeFrom(calculationProfileRequest);
        calculationProfile.setIsotope(isotope);
        CalculationProfile calculationProfileId = calculationProfileRepository.save(calculationProfile);
        return calculationProfileId.getId();
    }

    @Transactional
    public void updateCalculationProfile(Integer studyId, CalculationProfileInfo calculationProfileInfo) {
        Integer calculationProfileId = calculationProfileInfo.getCalculationProfileId();
        CalculationProfile calculationProfile = getValidCalculationProfile(calculationProfileId);
        CalculationProfile updateCalculationProfile = calculationProfileMapper.partialUpdateCalculationProfile(calculationProfileInfo, calculationProfile);
        calculationProfileRepository.save(updateCalculationProfile);
        patientInjectionService.recalculateMachineFillsForStudy(studyId);
    }

    private Isotope getStudyIsotopeFrom(CalculationProfileRequest calculationProfileRequest) {
        Integer studyId = calculationProfileRequest.getStudyId();
        Study study = getValidStudy(studyId);
        return study.getIsotope();
    }

    private Study getValidStudy(Integer studyId) {
        return studyRepository.findStudyBy(studyId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("studyId", studyId));
    }

    @Transactional
    public void removeCalculationProfile(Integer calculationProfileId) {
        calculationProfileRepository.deleteById(calculationProfileId);
    }

    private CalculationProfile getValidCalculationProfile(Integer calculationProfileId) {
        return calculationProfileRepository.findCalculationProfileBy(calculationProfileId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("calculationProfileId", calculationProfileId));
    }
}
