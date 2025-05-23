package ee.bcs.dosesyncback.service.login;

import ee.bcs.dosesyncback.controller.login.dto.LoginResponse;
import ee.bcs.dosesyncback.infrastructure.exception.ForbiddenException;
import ee.bcs.dosesyncback.persistence.login.LoginMapper;
import ee.bcs.dosesyncback.persistence.user.User;
import ee.bcs.dosesyncback.persistence.login.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static ee.bcs.dosesyncback.persistence.user.UserStatus.ACTIVE;
import static ee.bcs.dosesyncback.infrastructure.Error.INCORRECT_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginMapper loginMapper;
    private final LoginRepository loginRepository;

    public LoginResponse login(String username, String password) {
        User user = getValidUser(username, password);
        return loginMapper.toLoginResponse(user);
    }

    private User getValidUser(String username, String password) {
        return loginRepository.findUserBy(username, password, ACTIVE.getCode())
                .orElseThrow(() -> new ForbiddenException(
                        INCORRECT_CREDENTIALS.getMessage(),
                        INCORRECT_CREDENTIALS.getErrorCode()));
    }
}
