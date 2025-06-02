package ee.bcs.dosesyncback.controller.study;

import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.controller.study.dto.StudyRequest;
import ee.bcs.dosesyncback.controller.study.dto.StudyResult;
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
            summary = "Leiab kõik uuringud.",
            description = "Tagastab kõik uuringud koos olemasoleva infoga, sh null väärtused.")
    public List<StudyInfo> getAllStudies() {
        return studyService.getAllStudies();
    }

    @PostMapping("/study")
    @Operation(
            summary = "Loob uue uuringu.",
            description = "Salvestab uue uuringu ja tagastab selle ID.")
    public Integer addStudy(@RequestBody StudyRequest studyRequest) {
        return studyService.addStudy(studyRequest);
    }

    @GetMapping("/study")
    @Operation(
            summary = "Leiab uuringu ID alusel.",
            description = "Tagastab valitud uuringu andmed.")
    public StudyInfo getStudy(@RequestParam Integer studyId) {
        return studyService.getStudy(studyId);
    }

    @GetMapping("/study/result")
    @Operation(
            summary = "Leiab uuringu tulemused.",
            description = "Tagastab uuringu arvutuslikud tulemused loputusmahu ja aktiivsuse näol.")
    public StudyResult getStudyResult(@RequestParam Integer studyId) {
        return studyService.getStudyResult(studyId);
    }

    @GetMapping("/study/machine-rinse-volume")
    @Operation(
            summary = "Arvutab ja salvestab uuringu Karli loputusmahu.",
            description = "Teostab simulatsiooni, valib optimaalse loputusmahu, salvestab tulemuse uuringu kirjes " +
                    "ning tagastab selle.")
    public BigDecimal calculateStudiesMachineRinseVolume(@RequestParam Integer studyId) {
        return studyService.calculateStudiesMachineRinseVolume(studyId);
    }

    @GetMapping("/study/machine-remaining-activity")
    @Operation(
            summary = "Leiab uuringu viimase loputuse aktiivsuse.",
            description = "Tagastab viimase süstega seotud aktiivsuse andmed.")
    public BigDecimal getStudiesLastMachineRinseActivity(@RequestParam Integer studyId) {
        return studyService.getStudiesLastMachineRinseActivity(studyId);
    }

    @PostMapping("/study/save-all")
    @Operation(
            summary = "Salvestab ja lõpetab uuringu.",
            description = "Täidab uuringu info ja märgib selle lõpetatuks ilma vastuseta.")
    public void addStudyInformation(@RequestParam Integer studyId) {
        studyService.addStudyInformation(studyId);
    }

    @PutMapping("/study-pending")
    @Operation(
            summary = "Uuendab pending uuringut.",
            description = "Muudab uuringu kuupäeva, masina ja isotoobi, ilma vastuseta.")
    public void updatePendingStudyInformation(@RequestParam Integer studyId, @RequestBody StudyRequest studyRequest) {
        studyService.updatePendingStudyInformation(studyId, studyRequest);
    }

    @DeleteMapping("/study-pending")
    @Operation(
            summary = "Kustutab pending uuringu.",
            description = "Kustutab uuringu ja sellega seotud seotud kirjed ilma vastuseta.")
    public void removeStudy(@RequestParam Integer studyId) {
        studyService.removeStudy(studyId);
    }
}
