package com.piotrek.gamecalendar.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    // TODO return my profile /me @PreAuthorize("hasRole('USER')")
    // TODO return users profile{username}
    // TODO return users favourite games
    // TODO return users created games @PreAuthorize("hasRole('MODERATOR or ADMIN')")

}
