package ee.bcs.dosesyncback.service.study;

import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudyRepository;
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
    private final DailyStudyRepository dailyStudyRepository;
    private final StudyMapper studyMapper;

    public List<StudyInfo> getAllStudies() {
        List<Study> studies = studyRepository.findAllBy(StudyStatus.PENDING.getCode());
        List<StudyInfo> studyInfos = studyMapper.toStudyInfos(studies);
        return studyInfos;
    }
}
