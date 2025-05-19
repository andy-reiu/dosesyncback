package ee.bcs.dosesyncback.controller.calculationprofile.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link CalculationProfile}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculationProfileDto implements Serializable {
    @NotNull
    private Integer machineId;
    @NotNull
    private Integer isotopeId;
    @NotNull
    private BigDecimal activity;
    @NotNull
    private String calibrationTime;
    @NotNull
    private String administrationTime;
    @NotNull
    private Integer activityBeforeFirst;
    @NotNull
    private Integer vialVolume;
}