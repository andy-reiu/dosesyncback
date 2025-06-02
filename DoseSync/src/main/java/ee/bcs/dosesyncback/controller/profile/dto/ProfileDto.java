package ee.bcs.dosesyncback.controller.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto implements Serializable {
    Integer profileId;
    Integer hospitalId;
    Integer roleId;
    String hospitalName;
    String occupationName;
    String nationalId;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    Instant createdAt;
    Instant updatedAt;
}