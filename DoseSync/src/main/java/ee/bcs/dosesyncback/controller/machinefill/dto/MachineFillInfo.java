package ee.bcs.dosesyncback.controller.machinefill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineFillInfo {
    private Integer machineFillId;
    private BigDecimal vialActivityBeforeInjection;
    private BigDecimal vialActivityAfterInjection;
    private BigDecimal injectedVolume;
    private BigDecimal remainingVolume;
}