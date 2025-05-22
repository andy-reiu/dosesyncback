package ee.bcs.dosesyncback.service.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
import ee.bcs.dosesyncback.persistence.machine.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineInfoMapper machineInfoMapper;
    private final MachineMapper machineMapper;
    private final HospitalRepository hospitalRepository;

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
    @Transactional
    public Machine addMachine(MachineDto machineDto) {
        if (machineRepository.serialNumberExistBy(machineDto.getMachineSerial())) {
            throw new ForbiddenException("See masin on juba süsteemis!",
                    403);
        }
        Machine machine = machineMapper.toMachine(machineDto);
        Machine saved = machineRepository.save(machine);
        return saved;

    }
}
