package com.piotrek.gamecalendar.game;

import com.piotrek.gamecalendar.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found game with id = " + id));
    }

    Page<Game> findPageOfGames(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }
}