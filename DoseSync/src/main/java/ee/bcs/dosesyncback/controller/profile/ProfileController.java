package ee.bcs.dosesyncback.controller.profile;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileDto;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
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
            summary = "Leiab süsteemist kõik kasutajate profiilid.",
            description = "Tagastab kogu profiili info, mis on profiili tabelis.")
    public List<ProfileDto> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/profile")
    @Operation(
            summary = "Tagastab uuringu teostanud kasutaja profiili.",
            description = "Leiab uuringu ID alusel kasutaja profiiliinfo, kes on uuringu loonud.")
    public ProfileStudyInfo getProfile(@RequestParam Integer studyId) {
        return profileService.getProfile(studyId);
    }

    @GetMapping("/userprofile")
    @Operation(
            summary = "Tagastab kasutaja profiili.",
            description = "Leiab kasutaja ID alusel seotud profiiliinfo.")
    public ProfileDto getUserProfile(@RequestParam Integer userId) {
        return profileService.getUserProfile(userId);
    }

    @PutMapping("/account-profile/update")
    @Operation(
            summary = "Uuendab kasutaja profiiliandmed.",
            description = "Uuendab profiili ja rolli info, sh haigla ja rolli ID alusel.")
    public void updateAccountProfile(@RequestParam Integer userId, @RequestBody ProfileDto profileDto) {
        profileService.updateAccountProfile(userId, profileDto);
    }

    @GetMapping("/api/profile-current")
    @Operation(
            summary = "Tagastab konkreetse profiili andmed profiili ID alusel.",
            description = "Leiab profiili ID põhjal profiili andmed, mida kasutatakse kasutaja profiili muutmiseks.")
    public ProfileUpdateInfo getCurrentUserProfile(@RequestParam Integer profileId) {
        return profileService.getCurrentUserProfile(profileId);
    }

    @PutMapping("/api/profile-update")
    @Operation(
            summary = "Uuendab profiili kontaktandmeid ja parooli.",
            description = "Võimaldab muuta profiili e-posti, telefoninumbrit ning vajadusel ka parooli.")
    public void updateProfile(@RequestBody ProfileUpdateInfo profileUpdateInfo) {
        profileService.updateProfile(profileUpdateInfo);
    }
}



