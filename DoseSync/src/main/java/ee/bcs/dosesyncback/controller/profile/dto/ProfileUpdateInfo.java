package ee.bcs.dosesyncback.controller.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateInfo {
    private Integer profileId;
    private String firstName;
    private String lastName;
    private String occupation;
    private String nationalId;
    private String email;
    private String phoneNumber;
    private String oldPassword;
    private String newPassword;
    private String imageData;

}
