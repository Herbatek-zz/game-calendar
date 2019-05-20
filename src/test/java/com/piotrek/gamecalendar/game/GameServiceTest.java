package com.piotrek.gamecalendar.game;

import com.piotrek.gamecalendar.test_data.GameTestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    @DisplayName("Should find game by id, when found game, return it")
    void shouldFindById_whenFound_thenReturn() {
        // given
        final Long EXPECTED_GAME_ID = 99L;
        Game expectedGame = GameTestObject.builder().counterStrikeGlobalOffensive().withId(EXPECTED_GAME_ID).build();
        given(gameRepository.findById(EXPECTED_GAME_ID)).willReturn(Optional.of(expectedGame));

        // when
        Game foundGame = gameService.findById(EXPECTED_GAME_ID);

        // then
        then(foundGame).isEqualTo(expectedGame);
        verify(gameRepository, times(1)).findById(EXPECTED_GAME_ID);
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    @DisplayName("Should find game by id, when not found game, then throw NotFoundException")
    void shouldFindById_whenNotFound_thenThrowNotFoundException() {
        // given
        final Long EXPECTED_GAME_ID = 99L;
        given(gameRepository.findById(EXPECTED_GAME_ID)).willReturn(Optional.empty());

        // when
        Throwable throwable = catchThrowable(() -> gameService.findById(EXPECTED_GAME_ID));

        // then
        then(throwable).hasMessage("Not found game with id = " + EXPECTED_GAME_ID);
        verify(gameRepository, times(1)).findById(EXPECTED_GAME_ID);
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    @DisplayName("Should return page of games, when no games, then return empty page")
    void shouldFindPageOfGames_whenNoGames_thenReturnEmptyPage() {
        // given
        Pageable pageable = PageRequest.of(1, 20);
        var emptyPageable = new PageImpl<Game>(Collections.emptyList(), pageable, 0);
        given(gameRepository.findAll(pageable)).willReturn(emptyPageable);

        // when
        Page<Game> returnedPage = gameService.findPageOfGames(pageable);

        // then
        then(returnedPage).isEmpty();
        verify(gameRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(gameRepository);
    }

    @Test
    @DisplayName("Should return page of games, when three games, then return page with three games")
    void shouldFindPageOfGames_whenThreeGames_thenReturnPageWithThreeGames() {
        // given
        Pageable pageable = PageRequest.of(1, 20);
        var expectedPage = new PageImpl<>(Arrays.asList(
                GameTestObject.builder().hearthstone().build(),
                GameTestObject.builder().theWitcher().build(),
                GameTestObject.builder().counterStrikeGlobalOffensive().build()
        ), pageable, 3);
        given(gameRepository.findAll(pageable)).willReturn(expectedPage);

        // when
        Page<Game> returnedPage = gameService.findPageOfGames(pageable);

        // then
        then(returnedPage).isNotEmpty().isEqualTo(expectedPage);
        verify(gameRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(gameRepository);
    }
}