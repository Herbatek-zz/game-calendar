package com.piotrek.gamecalendar.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrek.gamecalendar.AbstractIntegrationTest;
import com.piotrek.gamecalendar.exceptions.ErrorResponse;
import com.piotrek.gamecalendar.gameReleaseDate.GameReleaseDate;
import com.piotrek.gamecalendar.gameReleaseDate.GameReleaseDateRepository;
import com.piotrek.gamecalendar.gamingPlatform.GamingPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

class GameControllerTest extends AbstractIntegrationTest {

    @Resource
    private GameRepository gameRepository;

    @Resource
    private GameReleaseDateRepository gameReleaseDateRepository;

    @Resource
    private WebTestClient webTestClient;

    @Resource
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        gameRepository.deleteAll();
        gameReleaseDateRepository.deleteAll();
    }

    @Test
    void shouldReturnGameByGivenId() throws JsonProcessingException {
        // given
        var expectedGame = gameRepository.save(Game.builder()
                .id(1L)
                .name("The Witcher® 3: Wild Hunt")
                .releaseDates(Set.of()).build());

        // when
        var exchange = webTestClient.get()
                .uri("/games/" + expectedGame.getId())
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedGame));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenNotFoundGame() throws JsonProcessingException {
        // given
        var expectedResponse = new ErrorResponse(404, "Not found game with id 3");

        // when
        var exchange = webTestClient.get()
                .uri("/games/3")
                .exchange();

        // then
        exchange
                .expectStatus().is4xxClientError()
                .expectBody().json(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    void shouldReturnEmptySetWhenNoPremieresInThisMonth() throws JsonProcessingException {
        // given
        var expectedSet = new HashSet<Game>();

        // when
        var exchange = webTestClient.get()
                .uri("/games/this-month-premieres")
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedSet));
    }

    @Test
    void shouldReturnOneElementInSetWhenOneGameHasPremiereInThisMonth() throws JsonProcessingException {
        // given
        var gameReleaseDate = gameReleaseDateRepository.save(GameReleaseDate.builder()
                .gamingPlatform(GamingPlatform.builder().name("PC").build())
                .releaseDate(LocalDate.now()).build());

        var onlyGame = gameRepository.save(Game.builder()
                .id(3L)
                .name("Stronghold")
                .releaseDates(Set.of(gameReleaseDate)).build());

        var expectedSet = new HashSet<>();
        expectedSet.add(onlyGame);

        // when
        var exchange = webTestClient.get()
                .uri("/games/this-month-premieres")
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedSet));
    }
}