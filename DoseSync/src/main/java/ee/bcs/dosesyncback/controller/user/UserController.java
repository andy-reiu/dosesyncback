package ee.bcs.dosesyncback.controller.user;

import ee.bcs.dosesyncback.controller.user.dto.UserAccount;
import ee.bcs.dosesyncback.controller.user.dto.UserDto;
import ee.bcs.dosesyncback.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(
            summary = "Tagastab kõik kasutajad.",
            description = "Pärib andmebaasist kõik kasutajad ning tagastab nende andmed DTO kujul, " +
                    "sisaldades näiteks kasutaja ID ja muid seotud andmeid.")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    @Operation(
            summary = "Leiab kasutaja ID põhjal.",
            description = "Tagastab konkreetse kasutaja andmed, jättes parooli välja.")
    public UserDto findUser(@RequestParam Integer userId) {
        return userService.findUser(userId);
    }

    @PostMapping("/user")
    @Operation(
            summary = "Loob uue kasutaja.",
            description = "Salvestab uue kasutaja ja profiili koos rolli ja haiglaga.")
    public void createUserAccount(@RequestBody UserAccount userAccount) {
        userService.createUserAccount(userAccount);
    }

    @PutMapping("/user/update")
    @Operation(
            summary = "Uuendab kasutaja andmeid.",
            description = "Uuendab kasutaja rolli ja muid andmeid, vastust ei tagasta.")
    public void updateUser(@RequestParam Integer selectedUserId,
                           @RequestBody UserDto userDto) {
        userService.updateUser(selectedUserId, userDto);
    }
}







