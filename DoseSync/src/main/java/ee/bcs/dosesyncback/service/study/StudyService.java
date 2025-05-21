package ee.bcs.dosesyncback.service.study;

import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.study.Study;
import ee.bcs.dosesyncback.persistence.study.StudyMapper;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            Optional<CalculationProfile> optionalCalculationProfile = calculationProfileRepository.findCalculationProfileBy(studyId);
            if(optionalCalculationProfile.isPresent()){
                studyInfo.setIsotopeName(optionalCalculationProfile.get().getIsotope().getName());
            }
        }
        return studyInfos;
    }
}
