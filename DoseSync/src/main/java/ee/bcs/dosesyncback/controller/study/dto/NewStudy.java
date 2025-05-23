package ee.bcs.dosesyncback.controller.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewStudy implements Serializable {
    private Integer userId;
    private Integer machineId;
    private Integer isotopeId;
    private LocalDate studyDate;
}