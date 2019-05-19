package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.exceptions.BadRequestException;
import com.piotrek.gamecalendar.exceptions.NotFoundException;
import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.role.RoleRepository;
import com.piotrek.gamecalendar.security.oauth2.providers.AuthProvider;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import com.piotrek.gamecalendar.user.dto.UserProfile;
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

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
    }

    public UserProfile findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Not found user with username: " + username))
                .toUserProfile();
    }

    private User createUser(SignUpRequest signUpRequest) {

        final Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new BadRequestException("Role not set"));

        return User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .emailVerified(false)
                .authProvider(AuthProvider.local)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(Collections.singleton(userRole))
                .build();
    }
}