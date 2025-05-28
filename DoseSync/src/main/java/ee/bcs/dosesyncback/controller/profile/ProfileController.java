package ee.bcs.dosesyncback.controller.profile;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import ee.bcs.dosesyncback.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ProfileStudyInfo getProfile(@RequestParam Integer studyId){
        return profileService.getProfile(studyId);
    }
}
