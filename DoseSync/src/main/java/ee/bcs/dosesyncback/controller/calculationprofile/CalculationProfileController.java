package ee.bcs.dosesyncback.controller.calculationprofile;

import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileDto;
import ee.bcs.dosesyncback.service.CalculationProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculationProfileController {

    private final CalculationProfileService calculationProfileService;

    @PostMapping("/calculation-profile")
    @Operation(
            summary = "Uue calculation profile lisamine.",
            description = "Võtab andmebaasist kasutaja valitud machineId, isotopeId. Võtab kasutaja poolt ära" +
                    " täidetud seadmes oleva peaviaali andmed ning lisab need tabelisse")
    public void addCalculationProfile(@RequestBody CalculationProfileDto calculationProfileDto){
        calculationProfileService.addCalculationProfile(calculationProfileDto);
    }
}
