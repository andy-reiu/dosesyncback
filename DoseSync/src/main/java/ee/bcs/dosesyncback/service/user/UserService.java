package ee.bcs.dosesyncback.service.user;

import ee.bcs.dosesyncback.controller.user.dto.UserAccount;
import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import ee.bcs.dosesyncback.infrastructure.exception.PrimaryKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.hospital.Hospital;
import ee.bcs.dosesyncback.persistence.hospital.HospitalRepository;
import ee.bcs.dosesyncback.persistence.profile.Profile;
import ee.bcs.dosesyncback.persistence.profile.ProfileMapper;
import ee.bcs.dosesyncback.persistence.profile.ProfileRepository;
import ee.bcs.dosesyncback.persistence.role.Role;
import ee.bcs.dosesyncback.persistence.role.RoleRepository;
import ee.bcs.dosesyncback.persistence.user.User;
import ee.bcs.dosesyncback.persistence.user.UserMapper;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ProfileMapper profileMapper;
    private final HospitalRepository hospitalRepository;
    private final ProfileRepository profileRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = userMapper.toUserDtos(users);
        return userDtos;
    }

    @Transactional
    public void createUserAccount(UserAccount userAccount) {
        User user = userMapper.toUser(userAccount);
        Role role = roleRepository.findRoleBy(userAccount.getRoleName());
        user.setRole(role);
        userRepository.save(user);

        Profile profile = profileMapper.toProfile(userAccount);
        Hospital hospital = hospitalRepository.findHospitalBy(userAccount.getHospitalName());
        profile.setHospital(hospital);
        profile.setUser(user);
        profileRepository.save(profile);
    }

    public UserDto findUser(Integer userId) {
        User user = getValidUserBy(userId);
        UserDto userDto = userMapper.toUserDto(user);
        return userDto;
    }

    private User getValidUserBy(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PrimaryKeyNotFoundException("userId", userId));
        return user;
    }

    public void updateUser(Integer selectedUserId, UserDto userDto) {
        User user = userRepository.findUserBy(selectedUserId).orElseThrow(
                () -> new PrimaryKeyNotFoundException("selectedUserId", selectedUserId));
        Role role = roleRepository.findRoleBy(userDto.getRoleName());
        user.setRole(role);
        userMapper.partialUpdate(user, userDto);
        userRepository.save(user);
    }
}