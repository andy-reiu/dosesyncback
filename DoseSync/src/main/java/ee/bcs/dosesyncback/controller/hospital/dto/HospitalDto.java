package ee.bcs.dosesyncback.controller.hospital.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link ee.bcs.dosesyncback.persistence.hospital.Hospital}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto implements Serializable {
    @NotNull
    @Size(max = 255)
    private String name;
}