package ee.bcs.dosesyncback.persistence.profile;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileDto;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import ee.bcs.dosesyncback.persistence.hospital.HospitalMapper;
import ee.bcs.dosesyncback.persistence.user.UserMapper;
import org.mapstruct.*;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, HospitalMapper.class})
public interface ProfileMapper {
    @Mapping(source = "id",target = "profileId")
    @Mapping(source = "occupation",target = "occupation")
    @Mapping(source = "hospital.name",target = "hospitalName")
    @Mapping(source = "nationalId",target = "nationalId")
    @Mapping(source = "firstName",target = "firstName")
    @Mapping(source = "lastName",target = "lastName")
    @Mapping(source = "email",target = "email")
    @Mapping(source = "phoneNumber",target = "phoneNumber")
    @Mapping(source = "createdAt",target = "createdAt")
    @Mapping(source = "updatedAt",target = "updatedAt")
    ProfileDto toProfileDto(Profile profile);

    List<ProfileDto> toProfileDtos(List<Profile> profiles);

    @Mapping(source = "hospital.name", target = "hospitalName")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    ProfileStudyInfo toProfileStudyInfo(Profile profile);
}