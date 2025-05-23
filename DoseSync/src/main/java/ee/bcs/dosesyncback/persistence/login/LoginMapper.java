package ee.bcs.dosesyncback.persistence.login;

import ee.bcs.dosesyncback.controller.login.dto.LoginResponse;
import ee.bcs.dosesyncback.persistence.user.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoginMapper {
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.name", target = "roleName")
    LoginResponse toLoginResponse(User user);
}