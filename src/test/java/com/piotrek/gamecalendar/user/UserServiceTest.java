package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.role.RoleRepository;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import com.piotrek.gamecalendar.user.helpers.CustomTestModels;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  RoleRepository roleRepository;

    @Mock
    private  UserValidator userValidator;

    @Mock
    private  PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, roleRepository, passwordEncoder, userValidator);
    }

    @Test
    void shouldSaveWhenProperSignUpRequest() {
        // given
        SignUpRequest signUpRequest = CustomTestModels.createTest123SignUpRequest();
        User user = CustomTestModels.createTest123User();
        given(userRepository.save(any())).willReturn(user);
        given(passwordEncoder.encode(signUpRequest.getPassword())).willReturn(signUpRequest.getPassword());
        given(roleRepository.findByName(RoleName.ROLE_USER))
                .willReturn(Optional.of(Role.builder().name(RoleName.ROLE_USER).build()));

        // when
        User result = userService.save(signUpRequest);

        // then
        then(result).isEqualTo(user);
        verify(userRepository, times(1)).save(any());
        verify(userValidator, times(1)).checkUserBeforeSave(signUpRequest);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(roleRepository, times(1)).findByName(RoleName.ROLE_USER);
        verifyNoMoreInteractions(userRepository, roleRepository, passwordEncoder, userRepository);
    }
}