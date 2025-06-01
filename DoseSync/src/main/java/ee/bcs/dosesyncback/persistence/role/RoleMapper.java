package ee.bcs.dosesyncback.persistence.role;

import ee.bcs.dosesyncback.controller.role.dto.RoleDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {

    @Mapping(source = "id", target = "roleId")
    @Mapping(source = "name", target = "roleName")
    RoleDto toRoleDto(Role role);

    List<RoleDto> toRoleDto(List<Role> roles);
}