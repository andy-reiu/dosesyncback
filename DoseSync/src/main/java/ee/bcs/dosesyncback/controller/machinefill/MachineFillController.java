package ee.bcs.dosesyncback.controller.machinefill;

import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import ee.bcs.dosesyncback.persistence.dailystudy.DailyStudy;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import ee.bcs.dosesyncback.persistence.patient.Patient;
import ee.bcs.dosesyncback.service.machinefill.MachineFillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MachineFillController {

    private final MachineFillService machineFillService;

    @GetMapping("/machine-fill")
    @Operation(
            summary = "Leiab patsiendi masina fillid / arvutused.",
            description = "Tagastab listi arvutustest.")
    public void getAllMachineFills(@RequestParam Integer studyId) {
        machineFillService.getAllMachineFills(studyId);
    }
}
