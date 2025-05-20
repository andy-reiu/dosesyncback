package ee.bcs.dosesyncback.controller.isotope;

import ee.bcs.dosesyncback.controller.isotope.dto.IsotopeInfo;
import ee.bcs.dosesyncback.service.isotope.IsotopeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IsotopeController {

    private final IsotopeService isotopeService;

    //todo: lisada juurde, et kontrollib getAll'ga millises haiglast töötaja küsib. kontrollida, et status oleks õige
    @GetMapping("/isotopes")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist seadmete tabelist) kõik isotoobid.",
            description = "Tagastab info koos isotopeId ja isotopeName'ga")
    public List<IsotopeInfo> getAllIsotopes() {
        return isotopeService.getAllIsotopes();
    }

    //todo: Admini menüüsse isotoobi lisamine ja väljade muutmine (kõik väljad: name, half_life_hr, unit)
}
