package com.piotrek.gamecalendar.test_data;

import com.piotrek.gamecalendar.game.Game;
import com.piotrek.gamecalendar.game_release_date.GameReleaseDate;
import com.piotrek.gamecalendar.game_series.GameSeries;
import com.piotrek.gamecalendar.gaming_platform.GamingPlatform;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class GameTestObject {

    @Getter
    private Game game;

    private GamingPlatform windows = new GamingPlatform("Windows");
    private GamingPlatform os_x = new GamingPlatform("OS X");
    private GamingPlatform macOS = new GamingPlatform("MacOS");
    private GamingPlatform xbox = new GamingPlatform("Xbox 360");
    private GamingPlatform playstation = new GamingPlatform("PlayStation 3");
    private GamingPlatform linux = new GamingPlatform("Linux");

    private GameTestObject() {
        game = new Game();
    }

    public static GameTestObject builder() {
        return new GameTestObject();
    }

    public GameTestObject theWitcher() {

        GameReleaseDate windowsReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(windows)
                .releaseDate(LocalDate.of(2007, 10, 26))
                .build();
        GameReleaseDate osXReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(os_x)
                .releaseDate(LocalDate.of(2012, 4, 1))
                .build();

        this.game = Game.builder()
                .gameSeries(new GameSeries("The Witcher"))
                .name("The Witcher")
                .releaseDates(new HashSet<>(Arrays.asList(windowsReleaseDate, osXReleaseDate)))
                .build();
        return this;
    }

    public GameTestObject counterStrikeGlobalOffensive() {

        GameReleaseDate windowsReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(windows)
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        GameReleaseDate macOSReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(macOS)
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        GameReleaseDate xbox360ReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(xbox)
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        GameReleaseDate ps3ReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(playstation)
                .releaseDate(LocalDate.of(2012, 8, 1))
                .build();

        GameReleaseDate linuxReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(linux)
                .releaseDate(LocalDate.of(2014, 9, 1))
                .build();

        this.game = Game.builder()
                .gameSeries(new GameSeries("Counter Strike"))
                .name("Counter Strike Global Offensive")
                .releaseDates(new HashSet<>(Arrays.asList(
                        windowsReleaseDate,
                        macOSReleaseDate,
                        xbox360ReleaseDate,
                        ps3ReleaseDate,
                        linuxReleaseDate)))
                .build();

        return this;
    }

    public GameTestObject hearthstone() {

        GameReleaseDate windowsReleaseDate = GameReleaseDate.builder()
                .gamingPlatform(windows)
                .releaseDate(LocalDate.of(2014, 3, 11))
                .build();

        this.game = Game.builder()
                .name("Hearthstone")
                .releaseDates(new HashSet<>(Collections.singletonList(windowsReleaseDate)))
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
