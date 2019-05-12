package com.piotrek.gamecalendar.test_data;

import com.piotrek.gamecalendar.game.Game;
import com.piotrek.gamecalendar.game_release_date.GameReleaseDate;
import com.piotrek.gamecalendar.game_series.GameSeries;
import com.piotrek.gamecalendar.gaming_platform.GamingPlatform;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

public class GameTestObject {

    @Getter
    private Game game;

    private GameTestObject() {
        game = new Game();
    }

    public static GameTestObject builder() {
        return new GameTestObject();
    }

    public GameTestObject theWitcher() {
        var windowsReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("Windows"))
                .releaseDate(LocalDate.of(2007, 10, 26))
                .build();
        var osXReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("OS X"))
                .releaseDate(LocalDate.of(2012, 4, 1))
                .build();

        this.game = Game.builder()
                .gameSeries(new GameSeries("The Witcher"))
                .name("The Witcher")
                .releaseDates(Set.of(windowsReleaseDate, osXReleaseDate))
                .build();
        return this;
    }

    public GameTestObject counterStrikeGlobalOffensive() {
        var windowsReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("Windows"))
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        var macOSReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("MacOS"))
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        var xbox360ReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("Xbox 360"))
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        var ps3ReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("PlayStation 3"))
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        var linuxReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("Linux"))
                .releaseDate(LocalDate.of(2014, 9, 1))
                .build();

        this.game = Game.builder()
                .gameSeries(new GameSeries("Counter Strike"))
                .name("Counter Strike Global Offensive")
                .releaseDates(Set.of(windowsReleaseDate, macOSReleaseDate, xbox360ReleaseDate, ps3ReleaseDate, linuxReleaseDate))
                .build();

        return this;
    }

    public GameTestObject hearthstone() {
        var windowsReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(new GamingPlatform("Windows"))
                .releaseDate(LocalDate.of(2014, 3, 11))
                .build();

        this.game = Game.builder()
                .name("Hearthstone")
                .releaseDates(Set.of(windowsReleaseDate))
                .build();

        return this;
    }

    public GameTestObject withId(Long id) {
        game.setId(id);
        return this;
    }

    public Game build() {
        return this.game;
    }
}
