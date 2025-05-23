package ee.bcs.dosesyncback.controller.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private Integer userId;
    private Integer roleId;
    private String roleName;
    private String username;
    private String password;
    private String status;
}