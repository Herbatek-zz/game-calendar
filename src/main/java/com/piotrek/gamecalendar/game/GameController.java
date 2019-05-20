package com.piotrek.gamecalendar.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    @GetMapping("/{id}")
    public Game findById(@PathVariable Long id) {
        return gameService.findById(id);
    }

    @GetMapping
    public Page<Game> findPage(Pageable pageable) {
        return gameService.findPageOfGames(pageable);
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
