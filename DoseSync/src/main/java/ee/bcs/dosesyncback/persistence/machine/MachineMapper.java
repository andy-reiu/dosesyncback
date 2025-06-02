package ee.bcs.dosesyncback.persistence.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineDto;
import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MachineMapper {
    @Mapping(source = "machineId", target = "id")
    @Mapping(source = "hospitalId", target = "hospital.id")
    @Mapping(source = "machineName", target = "name")
    @Mapping(source = "machineSerial", target = "serialNumber")
    @Mapping(source = "machineDescription", target = "description")
    @Mapping(source = "machineStatus", target = "status")
    Machine toMachine(MachineDto machineDto);

    List<Machine> toMachine(List<MachineDto> machineDtos);

    @Mapping(source = "id", target = "machineId")
    @Mapping(source = "hospital.id", target = "hospitalId")
    @Mapping(source = "name", target = "machineName")
    @Mapping(source = "serialNumber", target = "machineSerial")
    @Mapping(source = "description", target = "machineDescription")
    @Mapping(source = "status", target = "machineStatus")
    MachineDto toMachineDto(Machine machine);

    List<MachineDto> toMachineDtos(List<Machine> machines);

    @Mapping(source = "machineName", target = "name")
    @Mapping(source = "machineSerial", target = "serialNumber")
    @Mapping(source = "machineDescription", target = "description")
    @Mapping(ignore = true, target = "hospital.id")
    void updateFromMachineDto(@MappingTarget Machine machine, MachineDto machineDto);

    @Mapping(source = "id", target = "machineId")
    @Mapping(source = "name", target = "machineName")
    MachineInfo toMachineInfo(Machine machine);

    List<MachineInfo> toMachineInfos(List<Machine> machine);
}