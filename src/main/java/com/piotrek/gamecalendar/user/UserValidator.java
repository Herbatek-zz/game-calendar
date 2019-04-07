package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.exceptions.BadRequestException;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void checkUserBeforeSave(SignUpRequest signUpRequest) {
        checkUsernameAvailability(signUpRequest);
        checkEmailAvailability(signUpRequest);
    }

    private void checkUsernameAvailability(SignUpRequest signUpRequest) {
        log.debug("Checking username {} availability", signUpRequest.getUsername());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            log.warn("Username {} is already taken", signUpRequest.getUsername());
            throw new BadRequestException("Username is already taken");
        }
    }

    private void checkEmailAvailability(SignUpRequest signUpRequest) {
        log.debug("Checking email {} availability", signUpRequest.getEmail());
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            log.warn("Email {} is already taken", signUpRequest.getEmail());
            throw new BadRequestException("Email is already taken");
        }
    }
}
