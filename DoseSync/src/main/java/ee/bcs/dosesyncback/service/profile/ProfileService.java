package ee.bcs.dosesyncback.service.profile;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileDto;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileUpdateInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
import ee.bcs.dosesyncback.persistence.profile.Profile;
import ee.bcs.dosesyncback.persistence.profile.ProfileMapper;
import ee.bcs.dosesyncback.persistence.profile.ProfileRepository;
import ee.bcs.dosesyncback.persistence.role.Role;
import ee.bcs.dosesyncback.persistence.role.RoleRepository;
import ee.bcs.dosesyncback.persistence.study.Study;
import ee.bcs.dosesyncback.persistence.study.StudyRepository;
import ee.bcs.dosesyncback.persistence.user.User;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final RoleRepository roleRepository;

    public ProfileStudyInfo getProfile(Integer studyId) {
        Study study = studyRepository.getReferenceById(studyId);
        Integer userId = study.getUser().getId();
        Profile profile = getValidProfile(userId);
        return profileMapper.toProfileStudyInfo(profile);
    }

    public List<ProfileDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profileMapper.toProfileDtos(profiles);
    }

    public ProfileDto getUserProfile(Integer userId) {
        Profile profile = getValidProfile(userId);
        return profileMapper.toProfileDto(profile);
    }

    public ProfileUpdateInfo getCurrentUserProfile(Integer profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("profileId", profileId));
        return profileMapper.toProfileUpdateInfo(profile);
    }

    @Transactional
    public void updateProfile(ProfileUpdateInfo profileUpdateInfo) {
        Profile profile = profileRepository.findById(profileUpdateInfo.getProfileId())
                .orElseThrow(() -> new ForeignKeyNotFoundException("profileId", profileUpdateInfo.getProfileId()));

        if (profileUpdateInfo.getEmail() != null && !profileUpdateInfo.getEmail().equals(profile.getEmail())) {
            profile.setEmail(profileUpdateInfo.getEmail());
        }

        if (profileUpdateInfo.getPhoneNumber() != null && !profileUpdateInfo.getPhoneNumber().equals(profile.getPhoneNumber())) {
            profile.setPhoneNumber((profileUpdateInfo.getPhoneNumber()));
        }

        if (profileUpdateInfo.getOldPassword() != null && profileUpdateInfo.getNewPassword() != null) {
            if (!profile.getUser().getPassword().equals(profileUpdateInfo.getOldPassword())) {
                profile.getUser().setPassword(profileUpdateInfo.getNewPassword());
            }
        }

        profileRepository.save(profile);
    }

    @Transactional
    public void updateAccountProfile(Integer userId, ProfileDto profileDto) {
        Profile profile = getValidProfile(userId);
        profileMapper.toUpdateProfile(profile, profileDto);
        updateProfileHospital(profile, profileDto);
        profileRepository.save(profile);
        User user = updateUserRole(userId, profileDto);
        userRepository.save(user);
    }

    private User updateUserRole(Integer userId, ProfileDto profileDto) {
        Integer roleId = profileDto.getRoleId();
        Role role = roleRepository.getReferenceById(roleId);
        User user = userRepository.getReferenceById(userId);
        user.setRole(role);
        return user;
    }

    private void updateProfileHospital(Profile profile, ProfileDto profileDto) {
        Hospital hospital = hospitalRepository.getReferenceById(profileDto.getHospitalId());
        profile.setHospital(hospital);
    }

    private Profile getValidProfile(Integer userId) {
        return profileRepository.findProfileBy(userId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("userId", userId));
    }
}


