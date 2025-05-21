package ee.bcs.dosesyncback.controller.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import ee.bcs.dosesyncback.persistence.machine.MachineDto;
import ee.bcs.dosesyncback.service.machine.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MachineController {

    private final MachineService machineService;

    //todo: lisada juurde, et kontrollib getAll'ga millises haiglast töötaja küsib.
    @GetMapping("/active-machines")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist seadmete tabelist) kõik aktiivsed masinad.",
            description = "Tagastab info koos machineId ja machineName'ga")
    public List<MachineInfo> getAllActiveMachines() {
        return machineService.getAllActiveMachines();
    }
    //todo: Admini menüüsse masinate lisamine ja väljade 0muutmine (kõik väljad: hospital_id (FK), name, serial_number, description, status)
    @GetMapping("/machines")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist machine tabelist) kõik masinad.",
            description = "Tagastab info koos machineId, machineName, hospitalId," +
                    " machineSeriali, descriptioni ja statusega "
    )
    public List<MachineDto> getAllMachines() {
        List<MachineDto> machineDtos = machineService.getAllMachines();
        return machineDtos;
    }
    @PostMapping("/machines")
    @Operation(summary = "Uue masina lisamine.", description = "Kõik väljad on kohustuslikud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Sellise nimega masin on juba süsteemis olemas!")
    })
    public void addMachine(MachineDto machineDto) {
        machineService.addMachine(machineDto);
    }

}
