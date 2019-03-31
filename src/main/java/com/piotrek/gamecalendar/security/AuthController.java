package com.piotrek.gamecalendar.security;

import com.piotrek.gamecalendar.exceptions.BadRequestException;
import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.role.RoleRepository;
import com.piotrek.gamecalendar.security.payload.JwtAuthenticationResponse;
import com.piotrek.gamecalendar.security.payload.LoginRequest;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import com.piotrek.gamecalendar.user.User;
import com.piotrek.gamecalendar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        checkUsernameAvailability(signUpRequest);
        checkEmailAvailability(signUpRequest);

        User result = userRepository.save(createUser(signUpRequest));

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(result);
    }

    private void checkUsernameAvailability(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            throw new BadRequestException("Username is already taken");
    }

    private void checkEmailAvailability(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            throw new BadRequestException("Email is already taken");
    }

    private User createUser(SignUpRequest signUpRequest) {

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new BadRequestException("Role not set"));

        return User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(Collections.singleton(userRole))
                .build();
    }
}
