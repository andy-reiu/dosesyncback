package ee.bcs.dosesyncback.controller.profile.dto;

import ee.bcs.dosesyncback.controller.hospital.dto.HospitalDto;
import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import jakarta.validation.constraints.NotNull;
import ee.bcs.dosesyncback.controller.hospital.dto.HospitalDto;
import jakarta.validation.constraints.Size;
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