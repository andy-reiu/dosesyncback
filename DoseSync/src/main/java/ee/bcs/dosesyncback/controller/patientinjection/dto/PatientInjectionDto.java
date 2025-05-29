package ee.bcs.dosesyncback.controller.patientinjection.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientInjectionDto {
    private Integer studyId;
    private String patientNationalId;
    private String acc;
    private BigDecimal injectionWeight;
    private BigDecimal injectionMbqKg;
    private LocalTime injectedTime;
    private BigDecimal injectedActivity;
}
