package ee.bcs.dosesyncback.persistence.isotope;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IsotopeDto {
    private Integer isotopeId;
    private String isotopeName;
    private BigDecimal halfLifeHr;
    private String unit;
    private String isotopeStatus;


}
