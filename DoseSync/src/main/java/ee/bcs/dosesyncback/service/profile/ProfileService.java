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
import ee.bcs.dosesyncback.util.ImageConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        ProfileUpdateInfo profileUpdateInfo = profileMapper.toProfileUpdateInfo(profile);
        Optional<UserImage> userImage = userImageRepository.findUserImageBy(profileId);
        if (userImage.isPresent()) {
            byte[] bytes = userImage.get().getData();
            String imageDataString = ImageConverter.bytesToString(bytes);
            profileUpdateInfo.setImageData(imageDataString);
        } else {
            profileUpdateInfo.setImageData(null);
        }

        return profileUpdateInfo;
    }

    @Transactional
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

        Optional<UserImage> userImage = userImageRepository.findUserImageBy(profile.getId());
        String newImageData;

        if (profileUpdateInfo.getImageData() != null) {
            newImageData = profileUpdateInfo.getImageData().trim();
        } else {
            newImageData = null;
        }

        boolean clientSentNoImage = newImageData.isEmpty();
        boolean storedImageExists = userImage.isPresent();

        if (storedImageExists && clientSentNoImage) {

            userImageRepository.deleteUserImageBy(profile);

        } else if (storedImageExists && !clientSentNoImage) {

            UserImage toUpdate = userImage.get();
            byte[] raw = ImageConverter.stringToBytes(newImageData);
            toUpdate.setData(raw);
            userImageRepository.save(toUpdate);

        } else if (!storedImageExists && !clientSentNoImage) {

            UserImage newImage = userImageMapper.toUserImage(profileUpdateInfo);
            newImage.setProfile(profile);
            userImageRepository.save(newImage);
        }

        profileRepository.save(profile);
    }

}

