package ee.bcs.dosesyncback.controller.study.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudyResult {
    private BigDecimal calculationMachineRinseVolume;
    private BigDecimal calculationMachineRinseActivity;
}
