package ee.bcs.dosesyncback.controller.isotope.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsotopeDto {
    private Integer isotopeId;
    private String isotopeName;
    private BigDecimal halfLifeHr;
    private String unit;
    private String isotopeStatus;


}
