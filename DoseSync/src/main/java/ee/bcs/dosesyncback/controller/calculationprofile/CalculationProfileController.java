package ee.bcs.dosesyncback.controller.calculationprofile;

import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileInfo;
import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileRequest;
import ee.bcs.dosesyncback.service.calculationprofile.CalculationProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalculationProfileController {

    private final CalculationProfileService calculationProfileService;

    @GetMapping("/calculation-profile")
    @Operation(
            summary = "Leiab uuringu calculation-profile.",
            description = "Tagastab listi kalkulatsiooni profiilidest")
    public List<CalculationProfileInfo> getAllStudiesCalculationProfiles(@RequestParam Integer studyId) {
        return calculationProfileService.getAllStudiesCalculationProfiles(studyId);
    }

    @PostMapping("/calculation-profile")
    @Operation(
            summary = "Lisab uue kalkulatsiooni profiili.",
            description = "Tagastab uue uuringule kuulu studyId")
    public Integer addCalculationProfile(@RequestBody CalculationProfileRequest calculationProfileRequest) {
        return calculationProfileService.addCalculationProfile(calculationProfileRequest);
    }

    @PutMapping("/calculation-profile")
    @Operation(
            summary = "Uuenda kalkulatsiooni profiili.",
            description = "Ei tagasta midagi.")
    public void updateCalculationProfile(@RequestParam Integer studyId,
                                         @RequestBody CalculationProfileInfo calculationProfileInfo) {
        calculationProfileService.updateCalculationProfile(studyId, calculationProfileInfo);
    }

    @DeleteMapping("/calculation-profile")
    @Operation(
            summary = "Kustutab kalkulatsiooni profiili.",
            description = "Ei tagasta midagi.")
    public void removeCalculationProfile(@RequestParam Integer calculationProfileId) {
        calculationProfileService.removeCalculationProfile(calculationProfileId);
    }
}
