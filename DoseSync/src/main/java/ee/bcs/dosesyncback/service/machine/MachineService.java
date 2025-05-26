package ee.bcs.dosesyncback.service.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
import ee.bcs.dosesyncback.persistence.machine.*;
import jakarta.transaction.Transactional;
import ee.bcs.dosesyncback.persistence.machine.MachineInfoMapper;
import ee.bcs.dosesyncback.persistence.machine.MachineRepository;
import ee.bcs.dosesyncback.persistence.machine.MachineStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;
    private final MachineInfoMapper machineInfoMapper;
    private final HospitalRepository hospitalRepository;

    public List<MachineInfo> getAllActiveMachines() {
        List<Machine> machines = machineRepository.findMachinesBy(MachineStatus.ACTIVE.getCode());
        List<MachineInfo> machineInfos = machineInfoMapper.toMachineInfos(machines);
        return machineInfos;
    }

    public List<MachineDto> getAllMachines() {
        List<Machine> machines = machineRepository.findAll();
        List<MachineDto> machineDtos = machineMapper.toMachineDtos(machines);

        return machineDtos;
    }

    public MachineDto getMachineById(int id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Obj not found"));

        return machineMapper.toMachineDto(machine);
    }

    @Transactional
    public Machine addMachine(MachineDto machineDto) {
        Integer hospitalId = machineDto.getHospitalId();
        if (hospitalId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hospitalId must be provided");
        }
        if (machineRepository.serialNumberExistBy(machineDto.getMachineSerial())) {
            throw new ForbiddenException("See masin on juba süsteemis!",
                    403);
        }
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("hospitalId"), hospitalId));
        Machine machine = machineMapper.toMachine(machineDto);
        machine.setHospital(hospital);
        Machine saved = machineRepository.save(machine);

        return saved;
    }

    @Transactional
    public MachineDto updateMachineStatus(int id, String status) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Obj not found"));

        machine.setStatus(status);

        machineRepository.save(machine);
        return getMachineById(id);
    }
}
