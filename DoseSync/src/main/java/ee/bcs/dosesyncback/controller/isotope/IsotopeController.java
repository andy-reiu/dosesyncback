package ee.bcs.dosesyncback.controller.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.infrastructure.error.ApiError;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeDto;
import ee.bcs.dosesyncback.service.isotope.IsotopeService;
import ee.bcs.dosesyncback.service.machine.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isotope")
@RequiredArgsConstructor
public class IsotopeController {

    private final IsotopeService isotopeService;
    private final MachineService machineService;

    //todo: lisada juurde, et kontrollib getAll'ga millises haiglast töötaja küsib.
    @GetMapping("/active-isotopes")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist seadmete tabelist) kõik isotoobid.",
            description = "Tagastab info koos isotopeId ja isotopeName'ga")
    public List<IsotopeInfo> getAllActiveIsotopes() {

        return isotopeService.getAllActiveIsotopes();
    }

    //todo: Admini menüüsse isotoobi lisamine ja väljade muutmine (kõik väljad: name, half_life_hr, unit)

    @GetMapping("/isotopes")
    @Operation(
            summary = "Leiab süsteeemist (admebaasist isotoopide tabelist) kõik isotoobid.",
            description = "Tagastab info koos isotopeId, isotopeName, halfLifeHr, unit ja statusega.")
    public List<IsotopeDto> getAllIsotopes() {
        List<IsotopeDto> isotopeDtos = isotopeService.getAllIsotopes();

        return isotopeDtos;
    }

    @PostMapping("/isotopes")
    @Operation(summary = "Uue isotoobi lisamine.", description = "kõik väljad peavad olema täidetud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Sellise nimega isotoop on juba süsteemis olemas", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public Isotope addIsotope(@RequestBody IsotopeDto isotopeDto) {

        return isotopeService.addIsotope(isotopeDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Kustutab isotoobi info")
    public ResponseEntity<Object> removeIsotope(@PathVariable("id") Integer isotopeId) {
        isotopeService.removeIsotope(isotopeId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/isotope-status")
    public IsotopeDto updateIsotopeStatus(@PathVariable Integer id, @RequestParam String status) {
        return isotopeService.updateIsotopeStatus(id, status);
    }

    @PatchMapping("/isotopes/{id}")
    public ResponseEntity<IsotopeDto> updateIsotope(@PathVariable Integer id, @RequestBody IsotopeDto isotopeDto) {
        IsotopeDto updatedIsotopeDto = isotopeService.updateIsotope(id, isotopeDto);

        return ResponseEntity.ok(updatedIsotopeDto);
    }
}



