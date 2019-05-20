package com.piotrek.gamecalendar.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piotrek.gamecalendar.AbstractIntegrationTest;
import com.piotrek.gamecalendar.exceptions.ErrorResponse;
import com.piotrek.gamecalendar.role.RoleRepository;
import com.piotrek.gamecalendar.test_data.UserTestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import javax.annotation.Resource;

class UserControllerTest extends AbstractIntegrationTest {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return current authenticated user")
    @WithMockUser
    void shouldGetCurrentAuthenticatedUserWhenFoundUserThenReturnUser() throws JsonProcessingException {
        // given
        var expectedResponse = userRepository.save(UserTestObject.builder()
                .piotrek()
                .withGoogleLogged()
                .withId(2)
                .withRoleUser(roleRepository)
                .withCorrectEmail()
                .withEmailVerified()
                .withCorrectPasswordPassword()
                .build())
                .toUserProfile();

        // when
        var exchange = webTestClient.get()
                .uri("api/users/me")
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    @DisplayName("Should return current authenticated user")
    void shouldGetCurrentAuthenticatedUserWhenNotFoundThenThrowNotFoundException() {
        // given

        // when

        // then

    }

    @Test
    @DisplayName("Should return user by username, when found, then return user")
    void shouldReturnUserByUsernameWhenFoundUserThenReturnUser() throws JsonProcessingException {
        // given
        var expectedResponse = userRepository.save(UserTestObject.builder()
                .piotrek()
                .withLocalLogged()
                .withRoleUser(roleRepository)
                .withCorrectEmail()
                .withEmailVerified()
                .withCorrectPasswordPassword()
                .build())
                .toUserProfile();

        // when
        var exchange = webTestClient.get()
                .uri("api/users/" + UserTestObject.PIOTREK_USERNAME)
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    @DisplayName("Should return user by username, when not found, then return NotFoundException")
    void shouldReturnUserByUsernameWhenNotFoundUserThenReturnNotFoundException() throws JsonProcessingException {
        // given
        final ErrorResponse expectedResult =
                new ErrorResponse(404, "Not found user with username: " + UserTestObject.PIOTREK_USERNAME);

        // when
        var exchange = webTestClient.get()
                .uri("api/users/" + UserTestObject.PIOTREK_USERNAME)
                .exchange();

        // then
        exchange
                .expectStatus().is4xxClientError()
                .expectBody().json(objectMapper.writeValueAsString(expectedResult));
    }
}