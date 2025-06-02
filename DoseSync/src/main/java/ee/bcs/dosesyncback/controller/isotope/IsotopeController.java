package ee.bcs.dosesyncback.controller.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeDto;
import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.infrastructure.error.ApiError;
import ee.bcs.dosesyncback.persistence.isotope.Isotope;
import ee.bcs.dosesyncback.service.isotope.IsotopeService;
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

    //todo: DLC: Lisada juurde haigla järgi leidmine, vaatab kus haiglast pärit ning otsib vastavad väljad.
    @GetMapping("/active-isotopes")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist seadmete tabelist) kõik isotoobid.",
            description = "Tagastab info koos isotopeId ja isotopeName'ga")
    public List<IsotopeInfo> getAllActiveIsotopes() {
        return isotopeService.getAllActiveIsotopes();
    }

    @GetMapping("/isotopes")
    @Operation(
            summary = "Leiab süsteeemist (admebaasist isotoopide tabelist) kõik isotoobid.",
            description = "Tagastab info koos isotopeId, isotopeName, halfLifeHr, unit ja statusega.")
    public List<IsotopeDto> getAllIsotopes() {
        return isotopeService.getAllIsotopes();
    }

    @PostMapping("/isotopes")
    @Operation(summary = "Uue isotoobi lisamine.", description = "kõik väljad peavad olema täidetud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Sellise nimega isotoop on juba süsteemis olemas", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public Isotope addIsotope(@RequestBody IsotopeDto isotopeDto) {
        return isotopeService.addIsotope(isotopeDto);
    }

    @PatchMapping("/{isotopeId}/isotope-status")
    @Operation(
            summary = "Uuendab isotoobi staatust.",
            description = "Võtab isotoobi ID ja uue staatuse stringina, uuendab isotoobi staatuse andmebaasis ning tagastab uuendatud isotoobi info.")
    public IsotopeDto updateIsotopeStatus(@PathVariable Integer isotopeId, @RequestParam String status) {
        return isotopeService.updateIsotopeStatus(isotopeId, status);
    }

    @PatchMapping("/isotopes/{isotopeId}")
    @Operation(
            summary = "Uuendab olemasoleva isotoobi andmeid.",
            description = "Võtab isotoobi ID ja IsotopeDto objekti, uuendab andmebaasis vastava isotoobi ning tagastab uuendatud isotoobi andmed.")
    public ResponseEntity<IsotopeDto> updateIsotope(@PathVariable Integer isotopeId, @RequestBody IsotopeDto isotopeDto) {
        IsotopeDto updatedIsotopeDto = isotopeService.updateIsotope(isotopeId, isotopeDto);
        return ResponseEntity.ok(updatedIsotopeDto);
    }

    @DeleteMapping("/{isotopeId}")
    @Operation(
            summary = "Kustutab isotoobi info (deaktiveerib isotoobi).",
            description = "Muutab isotoobi staatuseks 'DISABLED', ei eemalda kirjet andmebaasist. Tagastab HTTP 204 ilma sisuta.")
    public ResponseEntity<Object> removeIsotope(@PathVariable Integer isotopeId) {
        isotopeService.removeIsotope(isotopeId);
        return ResponseEntity.noContent().build();
    }
}



