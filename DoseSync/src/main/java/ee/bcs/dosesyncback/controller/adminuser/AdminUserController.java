package ee.bcs.dosesyncback.controller.adminuser;

import ee.bcs.dosesyncback.controller.adminuser.dto.AdminUserInfo;
import ee.bcs.dosesyncback.service.adminuser.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-view")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping("/users")
    @Operation(
            summary = "Tagastab kõikide kasutajate profiilid administraatorile",
            description = "Leiab ja tagastab nimekirja kõikidest kasutajatest koos nende profiili, rolli ja staatusega.")
    public List<AdminUserInfo> getAllUserProfilesForAdmin() {
        return adminUserService.getAllUserProfilesForAdmin();
    }

    @PatchMapping("/{userId}/user-status")
    @Operation(
            summary = "Uuendab kasutaja staatust",
            description = "Võtab kasutaja ID ja uue staatuse, uuendab kasutaja staatuse andmebaasis ning tagastab uuendatud kasutaja andmed.")
    public void updateUserStatus(@PathVariable Integer userId, @RequestParam String userStatus) {
        adminUserService.updateUserStatus(userId, userStatus);
    }
}
