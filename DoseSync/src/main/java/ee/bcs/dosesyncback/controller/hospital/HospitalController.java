package ee.bcs.dosesyncback.controller.hospital;

import ee.bcs.dosesyncback.persistence.hospital.HospitalDto;
import ee.bcs.dosesyncback.persistence.machine.MachineDto;
import ee.bcs.dosesyncback.service.hospital.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class HospitalController {


    private final HospitalService hospitalService;

    @GetMapping("/hospitals")
    @Operation(
            summary = "Leiab süsteemist(andmebaasist hospital) kõik vajalikud andmed",
            description = "Tagastab info koos hospitalId, hospitalName ja hospitalAddressiga")
    public List<HospitalDto> getAllHospitals() {
        List<HospitalDto> hospitalDtos = hospitalService.getAllHospitals();
        return hospitalDtos;
    }
    @PostMapping("/hospitals")
    @Operation(summary = "Uue haigla lisamine", description = "Kõik väljad peavad olema täidetud")
    public void addHospital(@RequestBody HospitalDto hospitalDto) {
        hospitalService.addHospital(hospitalDto);
    }
}
