package ee.bcs.dosesyncback.controller.user;


import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import ee.bcs.dosesyncback.persistence.user.UserRepository;
import ee.bcs.dosesyncback.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/users")
    @Operation(
            summary = "Leiab süsteemist (andmebaasist users tabelist) kõik kasutajad.",
            description = "Tagastab info koos userID ja userite andmetega'ga")
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return userDtos;

    }

    @PostMapping("/users")
    @Operation(
            summary = "Lisab uue kasutja.",
            description = "Tagastab uue kasutaja userId")
    public Integer createUser(@RequestBody UserDto userDto){
        return userService.createUser(new UserDto());
    }

}







