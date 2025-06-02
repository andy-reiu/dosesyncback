package ee.bcs.dosesyncback.controller.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {
    private Integer hospitalId;
    private String hospitalName;
    private String hospitalAddress;
}