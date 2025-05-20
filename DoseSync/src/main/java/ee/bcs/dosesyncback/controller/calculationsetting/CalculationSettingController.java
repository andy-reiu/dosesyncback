package ee.bcs.dosesyncback.controller.calculationsetting;

import ee.bcs.dosesyncback.persistence.calculationsetting.CalculationSettingDto;
import ee.bcs.dosesyncback.service.calculationsetting.CalculationSettingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
