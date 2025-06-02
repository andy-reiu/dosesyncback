package ee.bcs.dosesyncback.controller.calculationprofile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationProfileInfo {
    private Integer calculationProfileId;
    private BigDecimal calibratedActivity;
    private LocalTime calibrationTime;
    private Integer fillVolume;
}
