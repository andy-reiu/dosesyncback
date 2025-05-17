package ee.bcs.DoseSync.controller.login;

import ee.bcs.DoseSync.controller.login.dto.LoginResponse;
import ee.bcs.DoseSync.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    @Operation(summary = "Sisse logimine. Tagastab userId ja roleName")
    public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        LoginResponse loginResponse = loginService.login(username, password);
        return loginResponse;
    }
}
