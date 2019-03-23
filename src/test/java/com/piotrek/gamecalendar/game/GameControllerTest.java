package com.piotrek.gamecalendar.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrek.gamecalendar.AbstractIntegrationTest;
import com.piotrek.gamecalendar.exceptions.ErrorResponse;
import com.piotrek.gamecalendar.gameReleaseDate.GameReleaseDate;
import com.piotrek.gamecalendar.gameReleaseDate.GameReleaseDateRepository;
import com.piotrek.gamecalendar.gamingPlatform.GamingPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameControllerTest extends AbstractIntegrationTest {

    @Resource
    private GameRepository gameRepository;

    @Resource
    private GameReleaseDateRepository gameReleaseDateRepository;

    @Resource
    private TestRestTemplate testRestTemplate;

    private ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    @BeforeEach
    void beforeEach() {
        gameRepository.deleteAll();
        gameReleaseDateRepository.deleteAll();
    }

    @Test
    void shouldReturnGameByGivenId() {
        // given
        var expectedGame = gameRepository.save(Game.builder()
                .id(1L)
                .name("The Witcher® 3: Wild Hunt")
                .releaseDates(Set.of()).build());
        final var URL = "/games/" + expectedGame.getId();

        // when
        ResponseEntity<Game> response = testRestTemplate.getForEntity(URL, Game.class);

        // then
        assertAll(
                () -> then(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> then(response.getBody()).isEqualTo(expectedGame)
        );
    }

    @Test
    void shouldThrowNotFoundExceptionWhenNotFoundGame() {
        // given
        var expectedResponse = new ErrorResponse(404, "Not found game with id 3");

        // when
        var response = testRestTemplate.getForEntity("/games/3", ErrorResponse.class);

        // then
        assertAll(
                () -> then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND),
                () -> then(response.getBody()).isEqualTo(expectedResponse)
        );
    }

    @Test
    void shouldReturnEmptySetWhenNoPremieresInThisMonth() {
        // given
        var expectedSet = new HashSet<Game>();

        // when
        var response = testRestTemplate.getForEntity("/games/this-month-premieres", Set.class);

        // then
        assertNotNull(response.getBody());
        assertAll(
                () -> then(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> then(response.getBody()).isEqualTo(expectedSet),
                () -> then(response.getBody().size()).isEqualTo(0)
        );
    }

    @Test
    void shouldReturnOneElementInSetWhenOneGameHasPremiereInThisMonth() {
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
        var response = testRestTemplate.getForEntity("/games/this-month-premieres", Set.class);

        // then
        assertNotNull(response.getBody());
        assertAll(
                () -> then(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> then(response.getBody()).isEqualTo(objectMapper.writeValueAsString(expectedSet)),
                () -> then(response.getBody().size()).isEqualTo(expectedSet.size())
        );
    }
}