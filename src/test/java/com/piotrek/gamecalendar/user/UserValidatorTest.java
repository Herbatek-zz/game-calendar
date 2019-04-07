package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.user.helpers.CustomTestModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.*;

class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    private UserValidator userValidator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        userValidator = new UserValidator(userRepository);
    }

    @Test
    @DisplayName("Should throw BadRequestException when checkUserBeforeSave and given username has been already used")
    void shouldThrowBadRequestExceptionWhenCheckUserBeforeSaveAndGivenUsernameHasBeenUsed() {
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
    @DisplayName("Should throw BadRequestException when checkUserBeforeSave and email has been already used")
    void shouldThrowBadRequestExceptionWhenCheckUserBeforeSaveAndGivenEmailHasBeenUsed() {
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
    @DisplayName("Should do nothing when checkUserBeforeSave and username and email haven't been used")
    void shouldDoNothingWhenCheckUserBeforeSaveAndUsernameAndEmailHaveNotBeenUsed() {
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