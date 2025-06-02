package ee.bcs.dosesyncback.controller.patientinjection.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ee.bcs.dosesyncback.persistence.injection.Injection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientInjectionInfo implements Serializable {
    private Integer injectionId;
    private String acc;
    private String patientNationalId;
    private BigDecimal injectionWeight;
    private BigDecimal injectionMbqKg;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime injectedTime;
    private BigDecimal injectedActivity;
}