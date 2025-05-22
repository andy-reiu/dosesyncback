package ee.bcs.dosesyncback.persistence.injection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientInjectionMapper {

    @Mapping(source = "id", target = "injectionId")
    @Mapping(source = "weight", target = "weight")
    @Mapping(source = "mbqKg", target = "mbqKg")
    @Mapping(source = "injectedTime", target = "injectedTime")
    @Mapping(source = "injectedActivity", target = "injectedActivity")
    PatientInjectionInfo toPatientInjectionInfo(Injection injection);
}