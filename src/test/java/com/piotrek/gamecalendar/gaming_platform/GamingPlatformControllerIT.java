package com.piotrek.gamecalendar.gaming_platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piotrek.gamecalendar.AbstractIntegrationTest;
import com.piotrek.gamecalendar.test_data.GamingPlatformTestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;


class GamingPlatformControllerIT extends AbstractIntegrationTest {

    @Resource
    private GamingPlatformRepository gamingPlatformRepository;

    private final String URL_PREFIX = "/api/gaming-platforms";

    @BeforeEach
    void beforeEach() {
        gamingPlatformRepository.deleteAll();
    }

    @Test
    void shouldFindPage() throws JsonProcessingException {
        // given
        // when
        // then
    }

    @Test
    @DisplayName("Should find Gaming Platform by id, when found, then return")
    void shouldFindGamingPlatformById_whenFound_thenReturn() throws JsonProcessingException {
        // given
        var expectedGame = gamingPlatformRepository.save(GamingPlatformTestObject.builder().pc().build());

        // when
        var exchange = webTestClient.get().uri(URL_PREFIX + "/" + expectedGame.getId())
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedGame));
    }

    @Test
    @DisplayName("Should find Gaming Platform by id, when not found, then return status 404 with message")
    void shouldFindGamingPlatformById_whenNotFound_then404WithMessage() throws JsonProcessingException {

    }

    @Test
    void shouldCreate() {

    }

    @Test
    void shouldDelete() {
        // given
        // when
        // then
    }
}