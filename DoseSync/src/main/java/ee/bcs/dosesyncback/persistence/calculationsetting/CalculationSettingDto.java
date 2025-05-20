package ee.bcs.dosesyncback.persistence.calculationsetting;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalculationSettingDto {
    private Integer settingId;
    private BigDecimal settingMinActivity;
    private BigDecimal settingMaxActivity;
    private BigDecimal settingMinVolume;
    private BigDecimal settingMachineVolumeMax;
    private BigDecimal settingMachineVolumeMin;

}
