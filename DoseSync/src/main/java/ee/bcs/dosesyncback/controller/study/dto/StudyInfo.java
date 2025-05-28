package ee.bcs.dosesyncback.controller.study.dto;

import ee.bcs.dosesyncback.persistence.study.Study;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyInfo implements Serializable {
    private Integer studyId;
    private Integer machineId;
    private String machineName;
    private String studyDate;
    private Integer studyNrPatients;
    private String studyStartTime;
    private String studyEndTime;
    private BigDecimal studyTotalActivity;
    private String studyComment;
    private String studyStatus;
    private Integer calculationMachineRinseVolume;
    private Integer calculationMachineRinseActivity;
    private Integer isotopeId;
    private String isotopeName;
}