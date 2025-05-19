package ee.bcs.dosesyncback.persistence.calculationprofile;

import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileDto;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CalculationProfileMapper {

    @Mapping(target = "activity", source = "activity")
    @Mapping(target = "calibrationTime", source = "calibrationTime", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "administrationTime", source = "administrationTime", qualifiedByName = "stringToLocalTime")
    @Mapping(target = "activityBeforeFirst", source = "activityBeforeFirst")
    @Mapping(target = "vialVolume", source = "vialVolume")
    CalculationProfile toCalculationProfile(CalculationProfileDto calculationProfileDto);

    @Named("stringToLocalTime")
    default LocalTime stringToLocalTime(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }
        return LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}