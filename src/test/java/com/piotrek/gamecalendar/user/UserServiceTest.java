package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.exceptions.BadRequestException;
import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.role.RoleRepository;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should save user, when proper SignUpRequest")
    void shouldSaveUserWhenSaveAndSignUpRequestIsProper() {
        // given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("tyra_borer")
                .password("Oki6Thigh")
                .email("casimir.wate@hotmail.com")
                .build();
        User user = User.builder()
                .username("tyra_borer")
                .password("Oki6Thigh")
                .email("casimir.wate@hotmail.com")
                .roles(Set.of(new Role(RoleName.ROLE_USER)))
                .build();
        given(userRepository.save(any(User.class))).willReturn(user);
        given(passwordEncoder.encode(signUpRequest.getPassword())).willReturn(signUpRequest.getPassword());
        given(roleRepository.findByName(RoleName.ROLE_USER)).willReturn(Optional.of(new Role(RoleName.ROLE_USER)));

        // when
        User result = userService.save(signUpRequest);

        // then
        then(result).isEqualTo(user);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userValidator, times(1)).checkUserBeforeSave(signUpRequest);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleRepository, times(1)).findByName(RoleName.ROLE_USER);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("Should throw BadRequestException when invalid SignUpRequest")
    void shouldThrowBadRequestExceptionWhenInvalidSignUpRequest() {
        // given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .username("tyra_borer")
                .password("Oki6Thigh")
                .email("casimir.wate@hotmail.com")
                .build();
        willThrow(new BadRequestException("Email is already taken")).given(userValidator).checkUserBeforeSave(signUpRequest);

        // when
        Throwable throwable = catchThrowable(() -> userService.save(signUpRequest));

        // then
        then(throwable).hasMessageContaining("Email is already taken");
        verify(userValidator, times(1)).checkUserBeforeSave(signUpRequest);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }
}