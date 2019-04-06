package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.exceptions.BadRequestException;
import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.role.RoleRepository;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public User save(SignUpRequest signUpRequest) {
        userValidator.checkUserBeforeSave(signUpRequest);
        return userRepository.save(createUser(signUpRequest));
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
