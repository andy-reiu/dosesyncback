package ee.bcs.dosesyncback.controller.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    Integer id;
    String name;
}