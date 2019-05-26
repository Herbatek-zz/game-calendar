package com.piotrek.gamecalendar.security;

import com.piotrek.gamecalendar.security.payload.JwtAuthResponse;
import com.piotrek.gamecalendar.security.payload.LoginRequest;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import com.piotrek.gamecalendar.user.User;
import com.piotrek.gamecalendar.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authService.authenticateUser(loginRequest);
        String jwt = authService.getJwtToken(authentication);
        log.info("User {} has been successfully logged in", loginRequest.getUsernameOrEmail());
        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User registeredUser = userService.save(signUpRequest);
        log.info("User {} has been successfully registered", registeredUser.getUsername());

        //TODO verification token send to email
        String token = UUID.randomUUID().toString();

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/me")
                .buildAndExpand(registeredUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(registeredUser);
    }
}