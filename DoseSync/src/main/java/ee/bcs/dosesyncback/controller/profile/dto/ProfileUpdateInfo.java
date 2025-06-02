package ee.bcs.dosesyncback.controller.profile.dto;

import lombok.Data;

@Data
public class ProfileUpdateInfo {
    private Integer profileId;
    private String firstName;
    private String lastName;
    private String occupation;
    private String nationalId;
    private String email;
    private String phoneNumber;
    private String hospitalName;

    private String oldPassword;
    private String newPassword;

    private String imageData;

}
