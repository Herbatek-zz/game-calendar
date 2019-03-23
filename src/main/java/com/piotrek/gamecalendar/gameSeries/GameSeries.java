package com.piotrek.gamecalendar.gameSeries;

import com.piotrek.gamecalendar.game.Game;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class GameSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private Set<Game> games;
}
