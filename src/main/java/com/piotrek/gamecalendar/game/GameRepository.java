package com.piotrek.gamecalendar.game;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {
}
