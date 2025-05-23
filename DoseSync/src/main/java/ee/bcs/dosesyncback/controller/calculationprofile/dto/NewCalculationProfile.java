package ee.bcs.dosesyncback.controller.calculationprofile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCalculationProfile {
    private Integer studyId;
    private Integer isotopeId;
    private BigDecimal calibratedActivity;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime calibrationTime;
    private Integer fillVolume;
}
