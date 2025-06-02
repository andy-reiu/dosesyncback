package ee.bcs.dosesyncback.controller.isotope.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsotopeInfo {
    private Integer isotopeId;
    private String isotopeName;
}