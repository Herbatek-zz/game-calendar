package com.piotrek.gamecalendar.game;

import com.piotrek.gamecalendar.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameRepository gameRepository;

    @GetMapping("/{id}")
    public Game find(@PathVariable Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found game with id " + id));
    }

    @GetMapping("/this-month-premieres")
    public Set<Game> findThisMonthPremieres() {
        return gameRepository.findAllByMonthPremiere(LocalDate.now());
    }

}
