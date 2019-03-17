package com.piotrek.gamecalendar.game;

import com.piotrek.gamecalendar.AbstractIntegrationTest;
import com.piotrek.gamecalendar.exceptions.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertAll;

//@Testcontainers
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameControllerTest extends AbstractIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

//    @Container
//    private static final GenericContainer MARIA_DB_CONTAINER = new MariaDBContainer()
//            .withDatabaseName("testDB")
//            .withUsername("userTest")
//            .withPassword("strongPassword")
//            .withExposedPorts(3306);

    @BeforeEach
    void beforeEach() {
        gameRepository.save(Game.builder().id(1L).name("The Witcher® 3: Wild Hunt").build());
        gameRepository.save(Game.builder().id(2L).name("Dota 2").build());
    }

    @Test
    void shouldReturnGameByGivenId() {

//        System.out.println("###############################");
//        System.out.println("is Maria created: " + MARIA_DB_CONTAINER.isCreated());
//        System.out.println("is Maria running: " + MARIA_DB_CONTAINER.isRunning());
//        System.out.println("###############################");

        // given
        var expectedGame = Game.builder()
                .id(1L)
                .name("The Witcher® 3: Wild Hunt").build();

        // when
        ResponseEntity<Game> response = testRestTemplate.getForEntity("/games/1", Game.class);

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
        ResponseEntity<ErrorResponse> response = testRestTemplate.getForEntity("/games/3", ErrorResponse.class);

        // then
        assertAll(
                () -> then(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND),
                () -> then(response.getBody()).isEqualTo(expectedResponse)
        );
    }

}