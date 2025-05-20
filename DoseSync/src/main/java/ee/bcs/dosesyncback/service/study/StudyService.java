package ee.bcs.dosesyncback.service.study;

import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.study.Study;
import ee.bcs.dosesyncback.persistence.study.StudyMapper;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
import ee.bcs.dosesyncback.persistence.study.StudyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMapper studyMapper;
    private final CalculationProfileRepository calculationProfileRepository;

    public List<StudyInfo> getAllStudies() {
        List<Study> studies = studyRepository.findAll();
        List<StudyInfo> studyInfos = studyMapper.toStudyInfos(studies);
        for (StudyInfo studyInfo : studyInfos) {
            Integer studyId = studyInfo.getStudyId();
            CalculationProfile calculationProfile = calculationProfileRepository.findBy(studyId);
            studyInfo.setIsotopeName(calculationProfile.getIsotope().getName());
        }
        return studyInfos;
    }
}
