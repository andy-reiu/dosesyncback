package ee.bcs.dosesyncback.controller.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ee.bcs.dosesyncback.persistence.role.Role}
 */
@Value
public class RoleDto implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 10)
    String name;
}