package ee.bcs.dosesyncback.controller.calculationsetting;

import ee.bcs.dosesyncback.infrastructure.error.ApiError;
import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingDto;
import ee.bcs.dosesyncback.persistence.isotope.IsotopeDto;
import ee.bcs.dosesyncback.service.calculationsetting.CalculationSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalculationSettingController {
    private final CalculationSettingService calculationSettingService;

    // Leida süsteemist kõik valemi osad
    @GetMapping("/calculation-settings")
    @Operation(
            summary = "Leiab süsteemist(andmebaasist calculationsetting tabelist) kõik vajalikud andmed.",
            description = "Tagastab info koos settingsId, settingMinActiviy, settingMaxActivity, " +
                    "settingMinVol, settingMachineVolMax, settingMachineVolMin")
    public List<CalculationSettingDto> getAllCalculationSettings() {
        List<CalculationSettingDto> calculationSettingDtos = calculationSettingService.getAllCalculationSettings();
        return calculationSettingDtos;
    }
    @PostMapping("/calculation-settings")
    @Operation(summary = "Uue kalkulatsioonivalemi lisamine.", description = "Kõik väljad peavad olema täidetud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Vale sisestatud info",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public void addCalculationSetting(@RequestBody CalculationSettingDto calculationSettingDto) {
         calculationSettingService.addCalculationSetting(calculationSettingDto);
    }



}
