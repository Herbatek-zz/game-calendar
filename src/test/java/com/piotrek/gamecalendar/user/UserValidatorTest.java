package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.user.helpers.CustomTestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    @DisplayName("Should throw BadRequestException when given username has been already used")
    void shouldThrowBadRequestExceptionWhenGivenUsernameHasBeenUsed() {
        // given
        var signUpRequest = CustomTestModels.createTest123SignUpRequest();
        given(userRepository.existsByUsername(signUpRequest.getUsername())).willReturn(true);

        // when
        Throwable throwable = catchThrowable(() -> userValidator.checkUserBeforeSave(signUpRequest));

        //then
        then(throwable).hasMessageContaining("Username is already taken");
        verify(userRepository, times(1)).existsByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Should throw BadRequestException when email has been already used")
    void shouldThrowBadRequestExceptionGivenEmailHasBeenUsed() {
        // given
        var signUpRequest = CustomTestModels.createTest123SignUpRequest();
        given(userRepository.existsByUsername(anyString())).willReturn(false);
        given(userRepository.existsByEmail(signUpRequest.getEmail())).willReturn(true);

        // when
        Throwable throwable = catchThrowable(() -> userValidator.checkUserBeforeSave(signUpRequest));

        //then
        then(throwable).hasMessageContaining("Email is already taken");
        verify(userRepository, times(1)).existsByUsername(anyString());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Should do nothing when username and email haven't been used")
    void shouldDoNothingWhenUsernameAndEmailHaveNotBeenUsed() {
        // given
        var signUpRequest = CustomTestModels.createTest123SignUpRequest();
        given(userRepository.existsByUsername(anyString())).willReturn(false);
        given(userRepository.existsByEmail(anyString())).willReturn(false);

        // when
        userValidator.checkUserBeforeSave(signUpRequest);

        //then
        verify(userRepository, times(1)).existsByUsername(anyString());
        verify(userRepository, times(1)).existsByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }
}