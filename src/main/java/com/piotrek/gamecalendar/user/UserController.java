package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.user.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserProfile getMe() {
        // TODO return my profile /me @PreAuthorize("hasRole('USER')")
        return User.builder()
                .id(2L)
                .username("kenshin985")
                .email("kenshin985@email.com")
                .emailVerified(true)
                .imageUrl("http://bit.do/eSaxa")
                .password("$T43pd2kjuf2GwE3cDFUBgsQr")
                .roles(Set.of(Role.builder().name(RoleName.ROLE_USER).build()))
                .build()
                .toUserProfile();
    }
    // TODO return users profile{username}

    @GetMapping("/{username}")
    public UserProfile findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    // TODO return users favourite games
    // TODO return users created games @PreAuthorize("hasRole('MODERATOR or ADMIN')")

}
