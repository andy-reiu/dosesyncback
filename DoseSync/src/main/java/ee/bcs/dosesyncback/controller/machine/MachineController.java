package ee.bcs.dosesyncback.controller.machine;

import ee.bcs.dosesyncback.controller.machine.dto.MachineDto;
import ee.bcs.dosesyncback.controller.machine.dto.MachineInfo;
import ee.bcs.dosesyncback.service.machine.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/machine"))
public class MachineController {

    private final MachineService machineService;

    @GetMapping("/active-machines")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist masinate tabelist) kõik aktiivsed masinad.",
            description = "Tagastab info koos machineId ja machineName'ga.")
    public List<MachineInfo> getAllActiveMachines() {
        return machineService.getAllActiveMachines();
    }

    @GetMapping("/machines")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist machine tabelist) kõik masinad.",
            description = "Tagastab info koos machineId, machineName, hospitalId," +
                    " machineSeriali, descriptioni ja statusega ")
    public List<MachineDto> getAllMachines() {
        return machineService.getAllMachines();
    }

    @PostMapping("/machines")
    @Operation(summary = "Uue masina lisamine.", description = "Kõik väljad on kohustuslikud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Sellise nimega masin on juba süsteemis olemas!")})
    public void addMachine(@RequestBody MachineDto machineDto) {
        machineService.addMachine(machineDto);
    }

    @PatchMapping("/{machineId}/machine-status")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist machine tabelist) kõik masinad.",
            description = "Tagastab info koos machineId, machineName, hospitalId, machineSeriali, descriptioni ja statusega.")
    public void updateMachineStatus(@PathVariable Integer machineId, @RequestParam String machineStatus) {
        machineService.updateMachineStatus(machineId, machineStatus);
    }

    @PatchMapping("/machines/{machineId}")
    @Operation(
            summary = "Uuendab masina infot andmebaasis",
            description = "Võtab masin ID ja MachineDto objekti, uuendab masina andmed ja tagastab uuendatud masina info.")
    public ResponseEntity<MachineDto> updateMachine(@PathVariable Integer machineId, @RequestBody MachineDto machineDto) {
        MachineDto updatedMachineDto = machineService.updateMachine(machineId, machineDto);
        return ResponseEntity.ok(updatedMachineDto);
    }
}
