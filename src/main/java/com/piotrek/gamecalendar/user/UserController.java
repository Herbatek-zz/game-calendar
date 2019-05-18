package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.security.CurrentUser;
import com.piotrek.gamecalendar.security.UserPrincipal;
import com.piotrek.gamecalendar.user.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserProfile getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        log.info("Return user {}", userPrincipal.getName());
        return userService.findById(userPrincipal.getId())
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
