package ee.bcs.dosesyncback.controller.machinefill;

import ee.bcs.dosesyncback.controller.machinefill.dto.MachineFillInfo;
import ee.bcs.dosesyncback.service.machinefill.MachineFillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MachineFillController {

    private final MachineFillService machineFillService;

    @GetMapping("/machine-fills")
    @Operation(
            summary = "Leiab patsiendi masina fillid / arvutused.",
            description = "Tagastab listi arvutustest.")
    public List<MachineFillInfo> getAllMachineFills(@RequestParam Integer studyId) {
        return machineFillService.getAllMachineFills(studyId);
    }
}
