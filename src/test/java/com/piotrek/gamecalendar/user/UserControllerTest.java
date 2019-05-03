package com.piotrek.gamecalendar.user;

import com.piotrek.gamecalendar.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserControllerTest extends AbstractIntegrationTest {

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
}