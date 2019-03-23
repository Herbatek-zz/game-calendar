package com.piotrek.gamecalendar.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Set;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {

    // TODO: this is wrong, need to fix it after test fix
    @Query("SELECT g FROM Game g LEFT JOIN g.releaseDates r WHERE r.releaseDate = :localDate")
    Set<Game> findAllByMonthPremiere(@Param("localDate") LocalDate localDate);
}
