package ee.bcs.dosesyncback.persistence.hospital;

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