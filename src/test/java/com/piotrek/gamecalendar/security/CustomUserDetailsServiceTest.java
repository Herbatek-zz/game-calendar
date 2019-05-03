package com.piotrek.gamecalendar.security;

import com.piotrek.gamecalendar.user.User;
import com.piotrek.gamecalendar.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("Should load user by username or email, when found, then create user principal")
    void shouldLoadUserByUsernameWhenFoundByEmailThenCreateUserPrincipal() {
        // given
        final String EMAIL = "test44@email.com";

        final User userInDatabase = User.builder()
                .email(EMAIL)
                .build();

        final UserDetails createdUserPrincipalFromFoundedUser = UserPrincipal.create(userInDatabase);

        given(userRepository.findByEmailOrUsername(EMAIL, EMAIL)).willReturn(Optional.of(userInDatabase));

        // when
        UserDetails result = customUserDetailsService.loadUserByUsername(EMAIL);

        // then
        then(result).isEqualTo(createdUserPrincipalFromFoundedUser);
        verify(userRepository).findByEmailOrUsername(EMAIL, EMAIL);
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    @DisplayName("Should load user by username or email, when not found, then throw username not found exception")
    void shouldLoadUserByUsernameWhenNotFoundThenThrowUsernameNotFoundException() {
        // given
        final String EMAIL = "test44@email.com";
        given(userRepository.findByEmailOrUsername(EMAIL, EMAIL)).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> customUserDetailsService.loadUserByUsername(EMAIL));

        // then
        then(throwable).hasMessage("Not found user with username or email: " + EMAIL).hasSameClassAs(throwable);
        verify(userRepository).findByEmailOrUsername(EMAIL, EMAIL);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Should load user by id, when found, then create user principal")
    void shouldLoadUserByIdWhenFoundThenCreateUserPrincipal() {
        // given
        final long ID = 15;
        final User userInDatabase = User.builder()
                .id(ID)
                .build();
        final UserDetails createdUserPrincipalFromFoundedUser = UserPrincipal.create(userInDatabase);

        given(userRepository.findById(ID)).willReturn(Optional.of(userInDatabase));

        // when
        UserDetails result = customUserDetailsService.loadUserById(ID);

        // then
        then(result).isEqualTo(createdUserPrincipalFromFoundedUser);
        verify(userRepository).findById(ID);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("Should load user by id, when not found, then throw not found exception")
    void shouldLoadUserByIdWhenNotFoundThenNotFoundException() {
        // given
        final long ID = 15;
        given(userRepository.findById(ID)).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> customUserDetailsService.loadUserById(ID));

        // then
        then(throwable).hasMessage("Not found user with id: " + ID).hasSameClassAs(throwable);
        verify(userRepository).findById(ID);
        verifyNoMoreInteractions(userRepository);
    }
}