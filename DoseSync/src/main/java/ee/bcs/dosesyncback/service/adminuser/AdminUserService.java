package ee.bcs.dosesyncback.service.adminuser;

import ee.bcs.dosesyncback.controller.adminuser.dto.AdminUserInfo;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.profile.Profile;
import ee.bcs.dosesyncback.persistence.profile.ProfileRepository;
import ee.bcs.dosesyncback.persistence.user.User;

import ee.bcs.dosesyncback.persistence.user.UserMapper;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public List<AdminUserInfo> getAllUserProfilesForAdmin() {
        List<User> users = userRepository.findAll();
        List<AdminUserInfo> result = new ArrayList<>();
        for (User user : users) {
            Profile profile = profileRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceAccessException("Obj not found"));
            addUserToResult(user, profile, result);
        }
        return result;
    }

    private void addUserToResult(User user, Profile profile, List<AdminUserInfo> result) {
        AdminUserInfo adminUserInfo = new AdminUserInfo();
        adminUserInfo.setUserId(user.getId());
        adminUserInfo.setUsername(user.getUsername());
        adminUserInfo.setUserStatus(user.getStatus());
        adminUserInfo.setRoleId(user.getRole().getId());
        adminUserInfo.setRoleName(user.getRole().getName());

        if (profile != null) {
            adminUserInfo.setProfileId(profile.getId());
            adminUserInfo.setFirstName(profile.getFirstName());
            adminUserInfo.setLastName(profile.getLastName());
            adminUserInfo.setNationalId(profile.getNationalId());
        }
        result.add(adminUserInfo);
    }

    @Transactional
    public void updateUserStatus(Integer userId, String userStatus) {
        User user = getValidUser(userId);
        user.setStatus(userStatus);
        userRepository.save(user);
    }

    private User getValidUser(Integer userId) {
        return userRepository.findUserBy(userId)
                .orElseThrow(() -> new ForeignKeyNotFoundException(("userId"), userId));
    }
}
