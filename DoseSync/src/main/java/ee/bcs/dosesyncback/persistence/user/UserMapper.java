package ee.bcs.dosesyncback.persistence.user;

import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "role.id", target = "role.id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

}