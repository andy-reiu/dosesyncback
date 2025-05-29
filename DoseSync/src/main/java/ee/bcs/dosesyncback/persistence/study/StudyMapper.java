package ee.bcs.dosesyncback.persistence.study;

import ee.bcs.dosesyncback.controller.study.dto.StudyInfo;
import ee.bcs.dosesyncback.util.DateTimeConverter;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudyMapper {

    @Mapping(source = "id", target = "studyId")
    @Mapping(source = "machine.id", target = "machineId")
    @Mapping(source = "machine.name", target = "machineName")
    @Mapping(source = "date", target = "studyDate", qualifiedByName = "dateToString")
    @Mapping(source = "nrPatients", target = "studyNrPatients")
    @Mapping(source = "startTime", target = "studyStartTime", qualifiedByName = "timeToString")
    @Mapping(source = "endTime", target = "studyEndTime", qualifiedByName = "timeToString")
    @Mapping(source = "totalActivity", target = "studyTotalActivity")
    @Mapping(source = "comment", target = "studyComment")
    @Mapping(source = "status", target = "studyStatus")
    @Mapping(source = "calculationMachineRinseVolume", target = "calculationMachineRinseVolume")
    @Mapping(source = "calculationMachineRinseActivity", target = "calculationMachineRinseActivity")
    @Mapping(source = "isotope.id", target = "isotopeId")
    @Mapping(source = "isotope.name", target = "isotopeName")
    StudyInfo toStudyInfo(Study study);

    List<StudyInfo> toStudyInfos(List<Study> studies);


    @Named("dateToString")
    static String mapDate(LocalDate localDate) {
        return DateTimeConverter.localDateToString(localDate);
    }

    @Named("timeToString")
    static String mapTime(LocalTime localTime) {
        return DateTimeConverter.localTimeToString(localTime);
    }
}