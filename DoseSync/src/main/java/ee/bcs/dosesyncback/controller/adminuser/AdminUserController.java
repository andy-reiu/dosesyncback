package ee.bcs.dosesyncback.controller.adminuser;

import ee.bcs.dosesyncback.controller.adminuser.dto.AdminUserInfo;
import ee.bcs.dosesyncback.service.adminuser.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-view")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping("/users")
    public List<AdminUserInfo> getAllUserProfilesForAdmin() {
        return adminUserService.getAllUserProfilesForAdmin();
    }
    @PatchMapping("/{userId}/user-status")
    public void updateUserStatus(@PathVariable Integer userId, @RequestParam String userStatus) {
        adminUserService.updateUserStatus(userId, userStatus);
    }
}
