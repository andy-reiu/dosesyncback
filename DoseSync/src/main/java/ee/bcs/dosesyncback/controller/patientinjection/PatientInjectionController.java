package ee.bcs.dosesyncback.controller.patientinjection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionDto;
import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.service.patientinjection.PatientInjectionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientInjectionController {

    private final PatientInjectionService patientInjectionService;

    @GetMapping("/patient-injections")
    @Operation(
            summary = "Leiab patsiendi süsimised.",
            description = "Tagastab listi patsiendi süsimistest.")
    public List<PatientInjectionInfo> getAllStudiesPatientInjections(@RequestParam Integer studyId) {
        return patientInjectionService.getAllStudiesPatientInjections(studyId);
    }

    @PostMapping("/patient-injection")
    @Operation(
            summary = "Lisab patsiendi süstimist.",
            description = "Ei tagasta midagi.")
    public void addPatientInjection(@RequestParam Integer isotopeId, @RequestBody PatientInjectionDto patientInjectionDto) {
        patientInjectionService.addPatientInjection(isotopeId, patientInjectionDto);
    }

    @PutMapping("/patient-injection")
    @Operation(
            summary = "Uuendab patsiendi süstimist.",
            description = "Ei tagasta midagi.")
    public void updatePatientInjection(@RequestParam Integer injectionId,
                                       @RequestBody PatientInjectionDto patientInjectionDto) {
        patientInjectionService.updatePatientInjection(injectionId, patientInjectionDto);
    }

    @DeleteMapping("/patient-injection")
    @Operation(
            summary = "Kustutab patsiendi süstimise koos machine filliga.",
            description = "Ei tagasta midagi.")
    public void removePatientInjection(@RequestParam Integer patientInjectionId) {
        patientInjectionService.removePatientInjection(patientInjectionId);
    }

    @GetMapping("/patient-injection/template")
    @Operation(
            summary = "Kasutab kalkulatsiooni sätteid nii koostab patsiendi süstimise template.",
            description = "Tagastab template patsiendi süstimisest vajalike väljadega.")
    public PatientInjectionDto getStudyPatientInjectionTemplate(@RequestParam Integer studyId) {
        return patientInjectionService.getStudyPatientInjectionTemplate(studyId);
    }
}
