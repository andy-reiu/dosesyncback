package ee.bcs.dosesyncback.controller.calculationsetting;

import ee.bcs.dosesyncback.controller.calculationsetting.dto.CalculationSettingDto;
import ee.bcs.dosesyncback.infrastructure.error.ApiError;
import ee.bcs.dosesyncback.service.calculationsetting.CalculationSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calculation-setting")
public class CalculationSettingController {
    private final CalculationSettingService calculationSettingService;

    @GetMapping("/calculation-settings")
    @Operation(
            summary = "Leiab süsteemist(andmebaasist calculationsetting tabelist) kõik vajalikud andmed.",
            description = "Tagastab info koos settingsId, settingMinActiviy, settingMaxActivity, " +
                    "settingMinVol, settingMachineVolMax, settingMachineVolMin")
    public List<CalculationSettingDto> getAllCalculationSettings() {
        return calculationSettingService.getAllCalculationSettings();
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

    @PatchMapping("/calculation-settings/{id}")
    @Operation(
            summary = "Uuendab olemasolevat Calculation Settingut",
            description = "Võtab Calculation Setting ID ja osalised uuendatavad väljad, " +
                    "uuendab vastava kirje andmebaasis ning tagastab uuendatud andmed.")
    public ResponseEntity<CalculationSettingDto> updateCalculationSetting(@PathVariable Integer id,
                                                                          @RequestBody CalculationSettingDto calculationSettingDto) {
        CalculationSettingDto updatedCalculationSetting = calculationSettingService.updateCalculationSetting(id, calculationSettingDto);
        return ResponseEntity.ok(updatedCalculationSetting);
    }
}
