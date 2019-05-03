package com.piotrek.gamecalendar.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piotrek.gamecalendar.AbstractIntegrationTest;
import com.piotrek.gamecalendar.exceptions.ErrorResponse;
import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Set;

class UserControllerTest extends AbstractIntegrationTest {

    @Resource
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return current authenticated user")
    void shouldGetCurrentAuthenticatedUserWhenFoundUserThenReturnUser() {
        // given
        var expectedResponse = "ME";

        // when
        var exchange = webTestClient.get()
                .uri("api/users/me")
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class).isEqualTo(expectedResponse);
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
        final String USERNAME = "Piotrkacz22";

        var expectedResponse = userRepository.save(User.builder()
                .id(144L)
                .username(USERNAME)
                .roles(Set.of(Role.builder().name(RoleName.ROLE_USER).build()))
                .email("piotr999@email.com")
                .password("tajneHaselko999")
                .emailVerified(true)
                .imageUrl("obrazek.pl/342512")
                .build())
                .toUserProfile();

        // when
        var exchange = webTestClient.get()
                .uri("api/users/" + USERNAME)
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
        final String USERNAME = "Piotrkacz22";
        final ErrorResponse expectedResult =
                new ErrorResponse(404, "Not found user with username: " + USERNAME);

        // when
        var exchange = webTestClient.get()
                .uri("api/users/" + USERNAME)
                .exchange();

        // then
        exchange
                .expectStatus().is4xxClientError()
                .expectBody().json(objectMapper.writeValueAsString(expectedResult));
    }
}