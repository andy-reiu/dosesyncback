package ee.bcs.dosesyncback.controller.study;

import ee.bcs.dosesyncback.controller.study.dto.NewStudy;
import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.service.study.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/studies")
    @Operation(
            summary = "Leiab süsteemist kõik uuringud.",
            description = "Tagastab kõik studyl olemasoleva info (ka null väärtused).")
    public List<StudyInfo> getAllStudies() {
        return studyService.getAllStudies();
    }

    @PostMapping("/study")
    @Operation(
            summary = "Teeb uue uuringu.",
            description = "Tagastab uue uuringule kuulu studyId")
    public Integer addStudy(@RequestBody NewStudy newStudy) {
        return studyService.addStudy(newStudy);
    }
}
