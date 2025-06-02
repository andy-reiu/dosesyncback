package ee.bcs.dosesyncback.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    private Integer roleId;
    private String username;
    private String password;
    private String userStatus;
    private Integer hospitalId;
    private String occupationName;
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
