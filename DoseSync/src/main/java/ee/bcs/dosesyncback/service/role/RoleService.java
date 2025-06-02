package ee.bcs.dosesyncback.service.role;

import ee.bcs.dosesyncback.controller.role.dto.RoleDto;
import ee.bcs.dosesyncback.persistence.role.Role;
import ee.bcs.dosesyncback.persistence.role.RoleMapper;
import ee.bcs.dosesyncback.persistence.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toRoleDto(roles);
    }
}
