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

    @GetMapping("/study")
    @Operation(
            summary = "Leiab vastava study",
            description = "Tagastab kogu study")
    public StudyInfo getStudy(@RequestParam Integer studyId) {
        return studyService.getStudy(studyId);
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

    @PostMapping("/study/save-all")
    @Operation(
            summary = "Võtab vajalikkudest kohtadest andmeread ning täidab käimasoleva study andmed. " +
                    "Märgib uuringu kui completed.",
            description = "Ei tagasta midagi, salvestab study andmebaasi.")
    public void addStudyInformation(@RequestParam Integer studyId) {
        studyService.addStudyInformation(studyId);
    }

    @PutMapping("/study-pending")
    @Operation(
            summary = "Uuendab pending study uue kuupäeva ja isotoobiga.",
            description = "Ei tagasta midagi.")
    public void updatePendingStudyInformation(@RequestParam Integer studyId, @RequestBody NewStudy newStudy) {
        studyService.updatePendingStudyInformation(studyId, newStudy);
    }

    @DeleteMapping("/study-pending")
    @Operation(
            summary = "Kustutab pending study",
            description = "Ei tagasta midagi.")
    public void removePendingStudy(@RequestParam Integer studyId) {
        studyService.removePendingStudy(studyId);
    }
}
