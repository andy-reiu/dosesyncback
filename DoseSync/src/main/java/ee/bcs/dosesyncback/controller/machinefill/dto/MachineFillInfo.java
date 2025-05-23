package ee.bcs.dosesyncback.controller.machinefill.dto;

import ee.bcs.dosesyncback.persistence.machinefill.MachineFill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineFillInfo implements Serializable {
    private Integer machineFillId;
    private BigDecimal vialActivityBeforeInjection;
    private BigDecimal vialActivityAfterInjection;
    private BigDecimal injectedVolume;
    private BigDecimal remainingVolume;
}