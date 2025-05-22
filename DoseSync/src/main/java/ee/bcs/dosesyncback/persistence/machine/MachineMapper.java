package ee.bcs.dosesyncback.persistence.machine;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MachineMapper {

    @Mapping(source = "id", target = "machineId")
    @Mapping(source = "hospital.id", target = "hospitalId")
    @Mapping(source = "name", target = "machineName")
    @Mapping(source = "serialNumber", target = "machineSerial")
    @Mapping(source = "description", target = "machineDescription")
    @Mapping(source = "status", target = "machineStatus")
    MachineDto toMachineDto(Machine machine);

    List<MachineDto> toMachineDtos(List<Machine> machines);

    @Mapping(source = "machineId", target = "id")
    @Mapping(source = "hospitalId", target = "hospital.id")
    @Mapping(source = "machineName", target = "name")
    @Mapping(source = "machineSerial", target = "serialNumber")
    @Mapping(source = "machineDescription", target = "description")
    @Mapping(source = "machineStatus", target = "status")
    Machine toMachine(MachineDto machineDto);

    List<Machine> toMachines(List<MachineDto> machineDtos);


}