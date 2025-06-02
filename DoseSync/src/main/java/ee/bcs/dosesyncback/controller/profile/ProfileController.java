package ee.bcs.dosesyncback.controller.profile;


import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileDto;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileUpdateInfo;
import ee.bcs.dosesyncback.service.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/userprofile")
    public ProfileDto getUserProfile(@RequestParam Integer userId){
        return profileService.getUserProfile(userId);
    }

    @PutMapping("/account-profile/update")
    public void updateAccountProfile(@RequestParam Integer userId, @RequestBody ProfileDto profileDto){
        profileService.updateAccountProfile(userId, profileDto);
    }
    @GetMapping("/api/profile-current")
    public ProfileUpdateInfo getCurrentUserProfile(@RequestParam Integer profileId) {
        return profileService.getCurrentUserProfile(profileId);
    }

    @PutMapping("/api/profile-update")
    public void updateProfile(@RequestBody ProfileUpdateInfo profileUpdateInfo) {
        profileService.updateProfile(profileUpdateInfo);
    }
}



