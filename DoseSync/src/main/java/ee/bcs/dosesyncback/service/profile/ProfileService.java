package ee.bcs.dosesyncback.service.profile;

import ee.bcs.dosesyncback.controller.profile.dto.ProfileStudyInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.persistence.hospital.HospitalMapper;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
import ee.bcs.dosesyncback.persistence.profile.Profile;
import ee.bcs.dosesyncback.controller.profile.dto.ProfileDto;
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
    private final HospitalMapper hospitalMapper;
    private final HospitalRepository hospitalRepository;
    private final RoleRepository roleRepository;

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

    @Transactional
    public void updateProfile(Integer userId, ProfileDto profileDto) {
        Profile profile = profileRepository.findProfileBy(userId)
                .orElseThrow(() -> new ForeignKeyNotFoundException("userId", userId));
        profileMapper.toUpdateProfile(profile, profileDto);

        Hospital hospital = hospitalRepository.getReferenceById(profileDto.getHospitalId());
        profile.setHospital(hospital);

        Role role = roleRepository.getReferenceById(profileDto.getRoleId());
        User user = userRepository.getReferenceById(userId);
        user.setRole(role);

        userRepository.save(user);
        profileRepository.save(profile);
    }
}
