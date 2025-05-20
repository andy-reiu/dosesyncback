package ee.bcs.dosesyncback.controller.study;

import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.service.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping("study")
    public List<StudyInfo> getAllStudies(){
        return studyService.getAllStudies();
    }
}
