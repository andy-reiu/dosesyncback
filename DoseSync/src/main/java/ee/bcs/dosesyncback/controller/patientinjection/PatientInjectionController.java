package ee.bcs.dosesyncback.controller.patientinjection;

import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileInfo;
import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.service.patientinjection.PatientInjectionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientInjectionController {

    private final PatientInjectionService patientInjectionService;

    public PatientInjectionController(PatientInjectionService patientInjectionService) {
        this.patientInjectionService = patientInjectionService;
    }

    @GetMapping("/patient-injections")
    @Operation(
            summary = "Leiab patsiendi süsimised.",
            description = "Tagastab listi patsiendi süsimistest.")
    public List<PatientInjectionInfo> getAllStudiesPatientInjections(@RequestParam Integer studyId) {
        return patientInjectionService.getAllStudiesPatientInjections(studyId);
    }
}
