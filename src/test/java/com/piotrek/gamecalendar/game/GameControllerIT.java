package com.piotrek.gamecalendar.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.piotrek.gamecalendar.AbstractIntegrationTest;
import com.piotrek.gamecalendar.exceptions.ErrorResponse;
import com.piotrek.gamecalendar.game_release_date.GameReleaseDateRepository;
import com.piotrek.gamecalendar.gaming_platform.GamingPlatformRepository;
import com.piotrek.gamecalendar.test_data.GameTestObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

class GameControllerIT extends AbstractIntegrationTest {

    @Resource
    private GameRepository gameRepository;

    @Resource
    private GameReleaseDateRepository gameReleaseDateRepository;

    @Resource
    private GamingPlatformRepository gamingPlatformRepository;

    @BeforeEach
    void beforeEach() {
        gameRepository.deleteAll();
        gameReleaseDateRepository.deleteAll();
        gamingPlatformRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a game by given id, when the game exists")
    void shouldReturnGameByGivenIdWhenTheGameExists() throws JsonProcessingException {
        // given
        Game expectedGame = gameRepository.save(GameTestObject.builder().theWitcher().withId(1L).build());

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get()
                .uri("api/games/" + expectedGame.getId())
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedGame));
    }

    @Test
    @DisplayName("Should throw not found exception when game with given id doesn't exist")
    void shouldThrowNotFoundExceptionWhenNotFoundGame() throws JsonProcessingException {
        // given
        ErrorResponse expectedResponse = new ErrorResponse(404, "Not found game with id = 3");

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get()
                .uri("api/games/3")
                .exchange();

        // then
        exchange
                .expectStatus().is4xxClientError()
                .expectBody().json(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    @DisplayName("Should return empty page when no games at all")
    void shouldReturnEmptyPageWhenNoGames() throws JsonProcessingException {
        // given
        PageImpl<Game> expectedResponse = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 20), 0);

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get()
                .uri("api/games")
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedResponse));
    }

    @Test
    @DisplayName("Should return page with three games when three games are available")
    void shouldReturnPageWith3GamesWhen3GamesAreInDatabase() throws JsonProcessingException {
        // given
        Game witcher = gameRepository.save(GameTestObject.builder().theWitcher().build());
        Game csgo = gameRepository.save(GameTestObject.builder().counterStrikeGlobalOffensive().build());
        Game hearthstone = gameRepository.save(GameTestObject.builder().hearthstone().build());

        PageImpl<Game> expectedResponse = new PageImpl<>(Arrays.asList(witcher, csgo, hearthstone), PageRequest.of(0, 20), 3);

        // when
        WebTestClient.ResponseSpec exchange = webTestClient.get()
                .uri("api/games")
                .exchange();

        // then
        exchange
                .expectStatus().is2xxSuccessful()
                .expectBody().json(objectMapper.writeValueAsString(expectedResponse));
    }
}