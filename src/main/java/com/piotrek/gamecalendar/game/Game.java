package com.piotrek.gamecalendar.game;

import com.piotrek.gamecalendar.gameReleaseDate.GameReleaseDate;
import com.piotrek.gamecalendar.gameSeries.GameSeries;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private Set<GameReleaseDate> releaseDates;

    @ManyToOne
    private GameSeries gameSeries;
}
