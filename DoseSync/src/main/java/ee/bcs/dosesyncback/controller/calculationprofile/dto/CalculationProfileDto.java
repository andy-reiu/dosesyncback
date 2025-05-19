package ee.bcs.dosesyncback.controller.calculationprofile.dto;

import ee.bcs.dosesyncback.persistence.calculationprofile.CalculationProfile;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.persistence.machine.Machine;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

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