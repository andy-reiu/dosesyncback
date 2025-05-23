package ee.bcs.dosesyncback.controller.patientinjection.dto;

import ee.bcs.dosesyncback.persistence.injection.Injection;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * DTO for {@link Injection}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientInjectionInfo implements Serializable {
    private Integer injectionId;
    private String acc;
    private String patientNationalId;
    private BigDecimal injectionWeight;
    private BigDecimal injectionMbqKg;
    private LocalTime injectedTime;
    private BigDecimal injectedActivity;
}