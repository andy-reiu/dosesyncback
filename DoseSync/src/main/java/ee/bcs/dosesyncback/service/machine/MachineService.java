package ee.bcs.dosesyncback.service.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import ee.bcs.dosesyncback.persistence.machine.Machine;
import ee.bcs.dosesyncback.persistence.machine.MachineMapper;
import ee.bcs.dosesyncback.persistence.machine.MachineRepository;
import ee.bcs.dosesyncback.persistence.machine.MachineStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;

    public List<MachineInfo> getAllMachines() {

        List<Machine> machines = machineRepository.findAllBy(MachineStatus.ACTIVE.getCode());
        List<MachineInfo> machineInfos = machineMapper.toMachineInfos(machines);
        return machineInfos;
    }
}
