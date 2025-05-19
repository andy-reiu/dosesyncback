package ee.bcs.dosesyncback.persistence.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MachineMapper {

    @Mapping(source = "id", target = "machineId")
    @Mapping(source = "name", target = "machineName")
    MachineInfo toMachineInfo(Machine machine);

    List<MachineInfo> toMachineInfos(List<Machine> machine);
}