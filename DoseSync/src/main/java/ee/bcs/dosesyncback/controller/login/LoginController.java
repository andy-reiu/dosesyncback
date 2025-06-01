package ee.bcs.dosesyncback.controller.login;

import ee.bcs.dosesyncback.controller.login.dto.LoginRequest;
import ee.bcs.dosesyncback.controller.login.dto.LoginResponse;
import ee.bcs.dosesyncback.infrastructure.error.ApiError;
import ee.bcs.dosesyncback.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @Operation(summary = "Sisse logimine. Tagastab userId,roleName ja profileId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Vale kasutajanimi või parool", content = @Content(schema = @Schema(implementation = ApiError.class)))})
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
