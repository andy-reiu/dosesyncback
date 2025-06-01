package ee.bcs.dosesyncback.service.login;

import ee.bcs.dosesyncback.controller.login.dto.LoginResponse;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.infrastructure.exception.ForeignKeyNotFoundException;
import ee.bcs.dosesyncback.persistence.profile.Profile;
import ee.bcs.dosesyncback.persistence.profile.ProfileRepository;
import ee.bcs.dosesyncback.persistence.user.User;
import ee.bcs.dosesyncback.persistence.user.UserMapper;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ee.bcs.dosesyncback.persistence.user.UserStatus.ACTIVE;
import static ee.bcs.dosesyncback.infrastructure.Error.INCORRECT_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public LoginResponse login(String username, String password) {
        User user = getValidUser(username, password);

        Profile profile = profileRepository.findProfileBy(user.getId())
                .orElseThrow(() -> new ForeignKeyNotFoundException(("profileId"), user.getId()));
        LoginResponse loginResponse = userMapper.toLoginResponse(user); // only maps userId and roleName
        loginResponse.setProfileId(profile.getId());
        return loginResponse;
    }

    private User getValidUser(String username, String password) {
        return userRepository.findUserBy(username, password, ACTIVE.getCode())
                .orElseThrow(() -> new ForbiddenException(
                        INCORRECT_CREDENTIALS.getMessage(),
                        INCORRECT_CREDENTIALS.getErrorCode()));
    }
}
