package ee.bcs.dosesyncback.controller.user.dto;

import ee.bcs.dosesyncback.persistence.user.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto implements Serializable {
    Integer id;
    @NotNull
    RoleDto role;
    @NotNull
    @Size(max = 255)
    String username;
    @NotNull
    @Size(max = 255)
    String password;
    @NotNull
    String status;
}