package ee.bcs.dosesyncback.controller.profile.dto;

import ee.bcs.dosesyncback.controller.hospital.dto.HospitalDto;
import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import jakarta.validation.constraints.NotNull;
import ee.bcs.dosesyncback.controller.hospital.dto.HospitalDto;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
@Data
public class ProfileDto implements Serializable {
    Integer profileId;
    @NotNull
    String hospitalName;
    @Size(max = 255)
    String occupation;
    @NotNull
    @Size(max = 11)
    String nationalId;
    @NotNull
    @Size(max = 255)
    String firstName;
    @NotNull
    @Size(max = 255)
    String lastName;
    @NotNull
    @Size(max = 255)
    String email;
    @Size(max = 255)
    String phoneNumber;
    Instant createdAt;
    Instant updatedAt;
}