package ee.bcs.dosesyncback.service.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineDto;
import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
import ee.bcs.dosesyncback.persistence.machine.Machine;
import ee.bcs.dosesyncback.persistence.machine.MachineMapper;
import ee.bcs.dosesyncback.persistence.machine.MachineRepository;
import ee.bcs.dosesyncback.persistence.machine.MachineStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;
    private final HospitalRepository hospitalRepository;

    public List<MachineInfo> getAllActiveMachines() {
        List<Machine> machines = machineRepository.findMachinesBy(MachineStatus.ACTIVE.getCode());
        return machineMapper.toMachineInfos(machines);
    }

    public List<MachineDto> getAllMachines() {
        List<Machine> machines = machineRepository.findAll();
        return machineMapper.toMachineDtos(machines);
    }

    @Transactional
    public void addMachine(MachineDto machineDto) {
        Machine machine = checkAndAddMachine(machineDto);
        Hospital hospital = checkAndAddHospital(machineDto);
        machine.setHospital(hospital);
        machineRepository.save(machine);
    }

    @Transactional
    public void updateMachineStatus(Integer machineId, String machineStatus) {
        Machine machine = getValidMachine(machineId);
        machine.setStatus(machineStatus);
        machineRepository.save(machine);
    }

    @Transactional
    public MachineDto updateMachine(Integer machineId, MachineDto machineDto) {
        Machine machine = getValidMachine(machineId);
        machineMapper.updateFromMachineDto(machine, machineDto);
        Integer hospitalId = machineDto.getHospitalId();
        updateHospitalsMachine(hospitalId, machine);
        machineRepository.save(machine);
        return machineMapper.toMachineDto(machine);
    }

    private Hospital checkAndAddHospital(MachineDto machineDto) {
        Integer hospitalId = machineDto.getHospitalId();
        checkIfHospitalExists(hospitalId);
        return getValidHospital(hospitalId);
    }

    private Machine checkAndAddMachine(MachineDto machineDto) {
        checkIfMachineExists(machineDto);
        return machineMapper.toMachine(machineDto);
    }

    private void checkIfHospitalExists(Integer hospitalId) {
        if (hospitalId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hospitalId must be provided");
        }
    }

    private void checkIfMachineExists(MachineDto machineDto) {
        if (machineRepository.serialNumberExistBy(machineDto.getMachineSerial())) {
            throw new ForbiddenException("See masin on juba süsteemis!", 403);
        }
    }

    private void updateHospitalsMachine(Integer hospitalId, Machine machine) {
        if (hospitalId != null) {
            Hospital hospital = getValidHospital(hospitalId);
            machine.setHospital(hospital);
        }
    }

    private Machine getValidMachine(int machineId) {
        return machineRepository.findMachineBy(machineId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException(("machineId"), machineId));
    }

    private Hospital getValidHospital(Integer hospitalId) {
        return hospitalRepository.findHospitalBy(hospitalId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException(("hospitalId"), hospitalId));
    }
}
