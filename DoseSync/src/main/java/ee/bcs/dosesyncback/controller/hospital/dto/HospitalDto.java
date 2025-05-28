package ee.bcs.dosesyncback.controller.hospital.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HospitalDto {
    private Integer hospitalId;
    private String hospitalName;
    private String hospitalAddress;
}