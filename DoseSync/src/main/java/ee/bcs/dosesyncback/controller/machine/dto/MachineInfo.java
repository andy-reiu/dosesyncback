package ee.bcs.dosesyncback.controller.machine.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineInfo implements Serializable {
    private Integer machineId;
    @NotNull
    @Size(max = 255)
    private String machineName;
}