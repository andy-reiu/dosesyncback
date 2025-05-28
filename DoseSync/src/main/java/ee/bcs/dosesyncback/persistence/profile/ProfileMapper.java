package ee.bcs.dosesyncback.persistence.profile;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    Profile toEntity(ProfileStudyInfo profileStudyInfo);

    @Mapping(source = "hospital.name", target = "hospitalName")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    ProfileStudyInfo toProfileStudyInfo(Profile profile);
}