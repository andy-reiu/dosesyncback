package ee.bcs.dosesyncback.service.study;

import ee.bcs.dosesyncback.controller.study.dto.NewStudy;
import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfileRepository;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeRepository;
import ee.bcs.dosesyncback.persistence.machine.Machine;
import ee.bcs.dosesyncback.persistence.machine.MachineRepository;
import ee.bcs.dosesyncback.persistence.study.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyMapper studyMapper;
    private final CalculationProfileRepository calculationProfileRepository;
    private final NewStudyMapper newStudyMapper;
    private final IsotopeRepository isotopeRepository;
    private final MachineRepository machineRepository;

    public List<StudyInfo> getAllStudies() {
        List<Study> studies = studyRepository.findAll();
        List<StudyInfo> studyInfos = studyMapper.toStudyInfos(studies);
//        for (StudyInfo studyInfo : studyInfos) {
//            Integer studyId = studyInfo.getStudyId();
//            Optional<CalculationProfile> optionalCalculationProfile = calculationProfileRepository.findCalculationProfileBy(studyId);
//            optionalCalculationProfile.ifPresent(
//                    calculationProfile -> studyInfo.setIsotopeName(calculationProfile.getIsotope().getName()));
//        }
        return studyInfos;
    }

    @Transactional
    public Integer addStudy(NewStudy newStudy) {
        Study study = newStudyMapper.toStudy(newStudy);

        Integer isotopeId = newStudy.getIsotopeId();
        Isotope isotope = isotopeRepository.getReferenceById(isotopeId);
        study.setIsotope(isotope);

        Integer machineId = newStudy.getMachineId();
        Machine machine = machineRepository.getReferenceById(machineId);
        study.setMachine(machine);

        study.setStatus(StudyStatus.PENDING.getCode());

        Study savedStudy = studyRepository.save(study);
        return savedStudy.getId();
    }
}
