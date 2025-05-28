package ee.bcs.dosesyncback.controller.profile;


import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import ee.bcs.dosesyncback.persistence.profile.ProfileDto;
import ee.bcs.dosesyncback.service.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profiles")
    @Operation(
            summary = "Leiab süsteeemist (admebaasist profiilide tabelist) kõik kasutajate profiilid.",
            description = "Tagastab kogu profiili info, mis on profiili tabelis.")
    public List<ProfileDto> getAllProfiles() {
        List<ProfileDto> profileDtos = profileService.getAllProfiles();
        return profileDtos;
    }

    @GetMapping("/profile")
    public ProfileStudyInfo getProfile(@RequestParam Integer studyId) {
        return profileService.getProfile(studyId);
    }
}



