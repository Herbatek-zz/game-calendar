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
    @DisplayName("Should save user, when proper SignUpRequest, then save user")
    void shouldSaveUserWhenSignUpRequestIsValidThenSaveUser() {
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
    @DisplayName("Should save user, when invalid SignUpRequest, then throw BadRequestException")
    void shouldSaveUserWhenInvalidSignUpRequestThenThrowBadRequestException() {
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
        then(throwable).hasMessageContaining("Email is already taken").hasSameClassAs(throwable);
        verify(userValidator, times(1)).checkUserBeforeSave(signUpRequest);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("Should return user by id, when user exists, then return user")
    void shouldFindUserByIdWhenFoundUserThenReturnUser() {
        // given
        final long ID = 15;
        final User expectedUser = User.builder().username("Grzesku69").id(ID).build();

        given(userRepository.findById(ID)).willReturn(Optional.of(expectedUser));

        // when
        User result = userService.findById(ID);

        // then
        then(result).isEqualTo(expectedUser);
        verify(userRepository, times(1)).findById(ID);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("Should return user by id, when user doesn't exist, then throw NotFoundException")
    void shouldFindUserByIdWhenUserDoesntExistThenThrowNotFoundException() {
        // given
        final long ID = 15;

        given(userRepository.findById(ID)).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.findById(ID));

        // then
        then(throwable).hasMessageContaining("Not found user with id: " + ID).hasSameClassAs(throwable);
        verify(userRepository, times(1)).findById(15L);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("Should return user by username, when user exists, then return user")
    void shouldFindUserByUsernameWhenFoundUserThenReturnUser() {
        // given
        final long ID = 15;
        final String USERNAME = "Grzesku96";
        final User expectedUser = User.builder().username(USERNAME).id(ID).build();

        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.of(expectedUser));

        // when
        User result = userService.findByUsername(USERNAME);

        // then
        then(result).isEqualTo(expectedUser);
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }

    @Test
    @DisplayName("Should return user by username, when user doesn't exist, then throw NotFoundException")
    void shouldFindUserByUsernameWhenUserDoesntExistThenThrowNotFoundException() {
        // given
        final String USERNAME = "Grzesku96";
        given(userRepository.findByUsername(USERNAME)).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> userService.findByUsername(USERNAME));

        // then
        then(throwable).hasMessageContaining("Not found user with username: " + USERNAME).hasSameClassAs(throwable);
        verify(userRepository, times(1)).findByUsername(USERNAME);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }
}