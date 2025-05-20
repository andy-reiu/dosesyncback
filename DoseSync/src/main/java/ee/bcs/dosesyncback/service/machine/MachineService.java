package ee.bcs.dosesyncback.service.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import ee.bcs.dosesyncback.persistence.machine.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineInfoMapper machineInfoMapper;
    private final MachineMapper machineMapper;

    public List<MachineInfo> getAllActiveMachines() {

        List<Machine> machines = machineRepository.findAllBy(MachineStatus.ACTIVE.getCode());
        List<MachineInfo> machineInfos = machineInfoMapper.toMachineInfos(machines);
        return machineInfos;
    }


    public List<MachineDto> getAllMachines() {
        List<Machine> machines = machineRepository.findAll();

       List<MachineDto> machineDtos = machineMapper.toMachineDtos(machines);

       return machineDtos;

    }
}
