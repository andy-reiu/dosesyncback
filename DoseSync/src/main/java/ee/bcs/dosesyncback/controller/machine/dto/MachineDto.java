package ee.bcs.dosesyncback.controller.machine.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto {
    private Integer machineId;
    private Integer hospitalId;
    private String machineName;
    private String machineSerial;
    private String machineDescription;
    private String machineStatus;
}
