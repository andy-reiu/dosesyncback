package ee.bcs.dosesyncback.controller.role;

import ee.bcs.dosesyncback.controller.role.dto.RoleDto;
import ee.bcs.dosesyncback.service.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    @Operation(
            summary = "Leiab andmebaasist kõik rollid.",
            description = "Tagastab kõik rollid")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }
}
