package ee.bcs.dosesyncback.controller.machine.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineDto {
    private Integer machineId;
    private Integer hospitalId;
    private String machineName;
    private String machineSerial;
    private String machineDescription;
    private String machineStatus;


}
