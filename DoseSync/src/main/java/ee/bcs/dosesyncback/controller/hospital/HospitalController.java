package ee.bcs.dosesyncback.controller.hospital;

import ee.bcs.dosesyncback.controller.hospital.dto.HospitalDto;
import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.service.hospital.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("/hospitals")
    @Operation(
            summary = "Leiab süsteemist(andmebaasist hospital) kõik vajalikud andmed",
            description = "Tagastab info koos hospitalId, hospitalName ja hospitalAddressiga")
    public List<HospitalDto> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @PostMapping("/hospitals")
    @Operation(summary = "Uue haigla lisamine", description = "Kõik väljad peavad olema täidetud")
    public Hospital addHospital(@RequestBody HospitalDto hospitalDto) {
        return hospitalService.addHospital(hospitalDto);
    }

    @PatchMapping("/hospitals/{hospitalId}")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist hospital) kõik vajalikud andmed",
            description = "Tagastab info koos hospitalId, hospitalName ja hospitalAddressiga")
    public ResponseEntity<HospitalDto> updateHospital(@PathVariable Integer hospitalId, @RequestBody HospitalDto hospitalDto) {
        HospitalDto updatedHospitalDto = hospitalService.updateHospital(hospitalId, hospitalDto);
        return ResponseEntity.ok(updatedHospitalDto);
    }
}
