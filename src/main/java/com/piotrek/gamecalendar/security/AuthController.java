package com.piotrek.gamecalendar.security;

import com.piotrek.gamecalendar.security.payload.JwtAuthenticationResponse;
import com.piotrek.gamecalendar.security.payload.LoginRequest;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import com.piotrek.gamecalendar.user.User;
import com.piotrek.gamecalendar.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        var authentication = authService.authenticateUser(loginRequest);

        String jwt = authService.getJwtToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User registeredUser = userService.save(signUpRequest);
        log.info("User {} has been successfully registered", registeredUser.getUsername());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(registeredUser.getUsername()).toUri();

        return ResponseEntity.created(location).body(registeredUser);
    }
}
