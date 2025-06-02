package ee.bcs.dosesyncback.service.profile;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileDto;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileUpdateInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.profile.Profile;
import ee.bcs.dosesyncback.persistence.profile.ProfileMapper;
import ee.bcs.dosesyncback.persistence.profile.ProfileRepository;
import ee.bcs.dosesyncback.persistence.study.Study;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import ee.bcs.dosesyncback.persistence.userimage.UserImage;
import ee.bcs.dosesyncback.persistence.userimage.UserImageMapper;
import ee.bcs.dosesyncback.persistence.userimage.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final UserImageMapper userImageMapper;


    public ProfileStudyInfo getProfile(Integer studyId) {
        Study study = studyRepository.getReferenceById(studyId);
        Integer userId = study.getUser().getId();
        Profile profile = profileRepository.findProfileBy(userId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("userId", userId));
        return profileMapper.toProfileStudyInfo(profile);
    }

    public List<ProfileDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        List<ProfileDto> profileDtos = profileMapper.toProfileDtos(profiles);
        return profileDtos;
    }

    public ProfileDto getUserProfile(Integer userId) {
        Profile profile = profileRepository.findProfileBy(userId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("userId", userId));
        return profileMapper.toProfileDto(profile);
    }

    public ProfileUpdateInfo getCurrentUserProfile(Integer profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("profileId", profileId));
        return profileMapper.toProfileUpdateInfo(profile);
    }

    public void updateProfile(ProfileUpdateInfo profileUpdateInfo) {
        Profile profile = profileRepository.findById(profileUpdateInfo.getProfileId())
                .orElseThrow(() -> new ForeignKeyNotFoundException("profileId", profileUpdateInfo.getProfileId()));

        if(profileUpdateInfo.getEmail() != null && !profileUpdateInfo.getEmail().equals(profile.getEmail())){
            profile.setEmail(profileUpdateInfo.getEmail());
        }

        if (profileUpdateInfo.getPhoneNumber() != null && !profileUpdateInfo.getPhoneNumber().equals(profile.getPhoneNumber())) {
            profile.setPhoneNumber((profileUpdateInfo.getPhoneNumber()));
        }

        if (profileUpdateInfo.getOldPassword() != null && profileUpdateInfo.getNewPassword() != null) {
            if(!profile.getUser().getPassword().equals(profileUpdateInfo.getOldPassword())){
                profile.getUser().setPassword(profileUpdateInfo.getNewPassword());
            }
        }

        if (profileUpdateInfo.getImageData() != null) {
            // Use mapper to convert base64 string to UserImage (with byte[] imageData)
            UserImage newImage = userImageMapper.toUserImage(profileUpdateInfo);

            // Link to profile
            newImage.setProfile(profile);

            // Optional: If you're enforcing 1 image per profile, delete old one or overwrite
            userImageRepository.save(newImage);
        }

       profileRepository.save(profile);
    }

}

