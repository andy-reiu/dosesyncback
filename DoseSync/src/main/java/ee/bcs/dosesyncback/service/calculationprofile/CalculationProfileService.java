package ee.bcs.dosesyncback.service.calculationprofile;

import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileInfo;
import ee.bcs.dosesyncback.controller.calculationprofile.dto.NewCalculationProfile;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileMapper;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
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

    public List<CalculationProfileInfo> getAllStudiesCalculationProfiles(Integer studyId) {
        List<CalculationProfile> calculationProfiles = calculationProfileRepository.findCalculationProfilesBy(studyId);
        List<CalculationProfileInfo> calculationProfileInfos = calculationProfileMapper.toCalculationProfileInfos(calculationProfiles);
        return calculationProfileInfos;
    }

    @Transactional
    public Integer addCalculationProfile(NewCalculationProfile newCalculationProfile) {
        CalculationProfile calculationProfile = calculationProfileMapper.toCalculationProfile(newCalculationProfile);

        Integer studyId = newCalculationProfile.getStudyId();
        Isotope isotope = studyRepository.getReferenceById(studyId).getIsotope();
        System.out.println(isotope.getName());

        calculationProfile.setIsotope(isotope);
        CalculationProfile calculationProfileId = calculationProfileRepository.save(calculationProfile);
        return calculationProfileId.getId();
    }

    @Transactional
    public void updateCalculationProfile(Integer studyId, CalculationProfileInfo calculationProfileInfo) {
        Integer calculationProfileId = calculationProfileInfo.getCalculationProfileId();
        CalculationProfile calculationProfile = calculationProfileRepository.findById(calculationProfileId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("calculationProfileId", calculationProfileId));
        calculationProfileMapper.partialUpdateCalculationProfile(calculationProfileInfo, calculationProfile);
        calculationProfileRepository.save(calculationProfile);
    }

    @Transactional
    public void removeCalculationProfile(Integer calculationProfileId) {
        calculationProfileRepository.deleteById(calculationProfileId);
    }
}
