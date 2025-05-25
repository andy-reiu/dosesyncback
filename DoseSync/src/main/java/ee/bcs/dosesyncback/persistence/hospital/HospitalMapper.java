package ee.bcs.dosesyncback.persistence.hospital;

import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HospitalMapper {

    @Mapping(source = "hospitalId", target = "id")
    @Mapping(source = "hospitalName", target = "name")
    @Mapping(source = "hospitalAddress", target = "address")
    Hospital toHospital(HospitalDto hospitalDto);

    List<Hospital> toHospitals(List<HospitalDto> hospitalDtos);

    @Mapping(source = "id", target = "hospitalId")
    @Mapping(source = "name", target = "hospitalName")
    @Mapping(source = "address", target = "hospitalAddress")
    HospitalDto toHospitalDto(Hospital hospital);

    List<HospitalDto> toHospitalDtos(List<Hospital> hospitals);


}