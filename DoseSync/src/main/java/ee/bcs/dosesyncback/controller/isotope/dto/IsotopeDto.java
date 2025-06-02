package ee.bcs.dosesyncback.controller.isotope.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsotopeDto {
    private Integer isotopeId;
    private String isotopeName;
    private BigDecimal halfLifeHr;
    private String unit;
    private String isotopeStatus;
}
