package ee.bcs.dosesyncback.controller.adminuser.dto;

import lombok.Data;

@Data
public class AdminUserInfo {
    private Integer userId;
    private String username;
    private String userStatus;
    private Integer roleId;
    private String roleName;

    private Integer profileId;
    private String firstName;
    private String lastName;
    private String nationalId;
}
