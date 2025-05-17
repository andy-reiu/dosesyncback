package ee.bcs.DoseSync.service.login;

import ee.bcs.DoseSync.controller.login.dto.LoginResponse;
import ee.bcs.DoseSync.infrastructure.exception.ForbiddenException;
import ee.bcs.DoseSync.persistence.user.User;
import ee.bcs.DoseSync.persistence.user.UserMapper;
import ee.bcs.DoseSync.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ee.bcs.DoseSync.AccountStatus.ACTIVE;
import static ee.bcs.DoseSync.infrastructure.Error.INCORRECT_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public LoginResponse login(String username, String password) {
        User user = getValidUser(username, password);
        LoginResponse loginResponse = userMapper.toLoginResponse(user);
        return loginResponse;
    }

    private User getValidUser(String username, String password) {
        User user = userRepository.findUserBy(username, password, ACTIVE.getCode())
                .orElseThrow(() -> new ForbiddenException(
                        INCORRECT_CREDENTIALS.getMessage(),
                        INCORRECT_CREDENTIALS.getErrorCode()));
        return user;
    }
}
