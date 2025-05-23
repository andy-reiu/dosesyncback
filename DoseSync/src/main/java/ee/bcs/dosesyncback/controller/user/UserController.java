package ee.bcs.dosesyncback.controller.user;


import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import ee.bcs.dosesyncback.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

private final UserService userService;

@GetMapping("/users")
@Operation(
        summary = "Leiab süsteemist (andmebaasist users tabelist) kõik kasutajad.",
        description = "Tagastab info koos userID ja userite andmetega'ga")
public List<UserDto> getAllUsers() {
    List<UserDto> userDtos = userService.getAllUsers();
    return userDtos;

}



    }







