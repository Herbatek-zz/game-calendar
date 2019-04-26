package com.piotrek.gamecalendar.game;

import com.piotrek.gamecalendar.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository gameRepository;

    @GetMapping("/{id}")
    public Game find(@PathVariable Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found game with id " + id));
    }

    @GetMapping
    public Page<Game> findPage(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

//     TODO logging
//     TODO Use PagedResponse + create GameRequest and GameResponse - and return it instead of Game
//    @GetMapping("/this-month-premieres")
//    public Set<Game> findThisMonthPremieres() {
//        return gameRepository.findAllByMonthPremiere(LocalDate.now());
//    }

//     TODO need to create role moderator or something like that
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    public Game create(@RequestBody @Valid Game game) {
//        return null;
//    }
}
