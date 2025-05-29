package ee.bcs.dosesyncback.controller.calculationsetting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalculationSettingDto {
    private Integer settingId;
    private BigDecimal settingMinActivity;
    private BigDecimal settingMaxActivity;
    private BigDecimal settingMinVolume;
    private BigDecimal settingMachineVolumeMax;
    private BigDecimal settingMachineVolumeMin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(type = "string", example = "00:30", description = "…")
    private LocalTime injectionInterval;
    private Double defaultPatientWeight;
    private BigDecimal activityPerKg;

    public void setInjectionInterval(LocalTime interval) {
        this.injectionInterval = interval;
    }

    @JsonSetter("injectionInterval")
    public void setInjectionInterval(Object raw) {
        if (raw instanceof Integer minutes) {
            this.injectionInterval = LocalTime.of(0, minutes);

        }
    }
}
