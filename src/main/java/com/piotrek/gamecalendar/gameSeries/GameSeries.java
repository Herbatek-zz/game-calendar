package com.piotrek.gamecalendar.gameSeries;

import com.piotrek.gamecalendar.game.Game;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class GameSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private Set<Game> games;

    public GameSeries(String name) {
        this.name = name;
    }
}
