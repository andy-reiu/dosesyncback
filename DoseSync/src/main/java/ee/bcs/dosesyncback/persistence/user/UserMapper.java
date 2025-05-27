package ee.bcs.dosesyncback.persistence.user;

import ee.bcs.dosesyncback.controller.login.dto.LoginResponse;
import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.name", target = "roleName")
    LoginResponse toLoginResponse(User user);

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")
    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

    @Mapping(source = "userId", target = "id")
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "roleName", target = "role.name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")
    User toUser(UserDto userDto);

    List<User> toUsers(List<UserDto> userDtos);}


