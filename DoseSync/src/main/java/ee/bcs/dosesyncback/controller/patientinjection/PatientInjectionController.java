package ee.bcs.dosesyncback.controller.patientinjection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.NewPatientInjectionRequest;
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
            summary = "Lisab patsiendi süstimise.",
            description = "Ei tagasta midagi.")
    public void newPatientInjection(@RequestBody NewPatientInjectionRequest newPatientInjectionRequest) {
        patientInjectionService.newPatientInjection(newPatientInjectionRequest);
    }
}
