package ee.bcs.dosesyncback.persistence.calculationsetting;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
    @Schema(
            type        = "string",
            format      = "HH:mm",
            example     = "00:30",
            description = "Time between injections (hours and minutes)"
    )
    private LocalTime injectionInterval;
    private Double defaultPatientWeight;
    private BigDecimal activityPerKg;


}
