package ee.bcs.dosesyncback.controller.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileStudyInfo implements Serializable {
    private String hospitalName;
    private String firstName;
    private String lastName;
}