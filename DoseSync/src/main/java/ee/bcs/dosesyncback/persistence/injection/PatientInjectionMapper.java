package ee.bcs.dosesyncback.persistence.injection;

import ee.bcs.dosesyncback.controller.patientinjection.dto.EditPatientInjectionRequest;
import ee.bcs.dosesyncback.controller.patientinjection.dto.NewPatientInjectionRequest;
import ee.bcs.dosesyncback.controller.patientinjection.dto.PatientInjectionInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientInjectionMapper {

    @Mapping(source = "id", target = "injectionId")
    @Mapping(source = "weight", target = "injectionWeight")
    @Mapping(source = "mbqKg", target = "injectionMbqKg")
    @Mapping(source = "injectedTime", target = "injectedTime")
    @Mapping(source = "injectedActivity", target = "injectedActivity")
    PatientInjectionInfo toPatientInjectionInfo(Injection injection);

    List<PatientInjectionInfo> toPatientInjectionInfos(List<Injection> injections);

    @Mapping(source = "acc", target = "acc")
    @Mapping(source = "injectionWeight", target = "weight")
    @Mapping(source = "injectionMbqKg", target = "mbqKg")
    @Mapping(source = "injectedTime", target = "injectedTime")
    @Mapping(source = "injectedActivity", target = "injectedActivity")
    Injection toInjection(NewPatientInjectionRequest newPatientInjectionRequest);

    @Mapping(source = "acc", target = "acc")
    @Mapping(source = "injectionWeight", target = "weight")
    @Mapping(source = "injectionMbqKg", target = "mbqKg")
    @Mapping(source = "injectedTime", target = "injectedTime")
    @Mapping(source = "injectedActivity", target = "injectedActivity")
    Injection toUpdateInjection(EditPatientInjectionRequest editPatientInjectionRequest, @MappingTarget Injection injection);
}