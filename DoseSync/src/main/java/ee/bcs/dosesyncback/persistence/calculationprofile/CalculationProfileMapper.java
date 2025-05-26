package ee.bcs.dosesyncback.persistence.calculationprofile;

import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileInfo;
import ee.bcs.dosesyncback.controller.calculationprofile.dto.CalculationProfileRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CalculationProfileMapper {

    @Mapping(source = "calibrationTime", target = "calibrationTime")
    @Mapping(source = "calibratedActivity", target = "calibratedActivity")
    @Mapping(source = "fillVolume", target = "fillVolume")
    @Mapping(source = "studyId", target = "study.id")
    @Mapping(source = "isotopeId", target = "isotope.id")
    CalculationProfile toCalculationProfile(CalculationProfileRequest calculationProfileRequest);

    @Mapping(source = "id", target = "calculationProfileId")
    @Mapping(source = "calibrationTime", target = "calibrationTime")
    @Mapping(source = "calibratedActivity", target = "calibratedActivity")
    @Mapping(source = "fillVolume", target = "fillVolume")
    CalculationProfileInfo toCalculationProfileInfo(CalculationProfile calculationProfile);

    List<CalculationProfileInfo> toCalculationProfileInfos(List<CalculationProfile> CalculationProfile);

    @Mapping(source = "calibrationTime", target = "calibrationTime")
    @Mapping(source = "calibratedActivity", target = "calibratedActivity")
    @Mapping(source = "fillVolume", target = "fillVolume")
    CalculationProfile partialUpdateCalculationProfile(CalculationProfileInfo calculationProfileInfo, @MappingTarget CalculationProfile calculationProfile);
}