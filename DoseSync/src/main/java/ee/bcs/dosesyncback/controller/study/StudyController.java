package ee.bcs.dosesyncback.controller.study;

import ee.bcs.dosesyncback.controller.study.dto.NewStudy;
import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.service.study.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/study/machine-rinse-volume")
    @Operation(
            summary = "Tagastab kogu uuringu kalkuleeritud Karli loputusmahu.",
            description = "Tagastab kalkuleeritud Karli loputusmahu.")
    public BigDecimal calculateStudiesMachineRinseVolume(@RequestParam Integer studyId) {
        return studyService.calculateStudiesMachineRinseVolume(studyId);
    }

    @GetMapping("/study/machine-remaining-activity")
    @Operation(
            summary = "Võtab andmebaasist viimase süstitud koguse aktiivsuse",
            description = "Tagastab kalkuleeritud aktiivsuse.")
    public BigDecimal getStudiesLastMachineRinseActivity(@RequestParam Integer studyId) {
        return studyService.getStudiesLastMachineRinseActivity(studyId);
    }
}
