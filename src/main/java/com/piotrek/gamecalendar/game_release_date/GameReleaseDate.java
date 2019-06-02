package com.piotrek.gamecalendar.game_release_date;

import com.piotrek.gamecalendar.gaming_platform.GamingPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameReleaseDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private GamingPlatform gamingPlatform;

    private LocalDate releaseDate;

    public GameReleaseDate(GamingPlatform gamingPlatform, LocalDate releaseDate) {
        this.gamingPlatform = gamingPlatform;
        this.releaseDate = releaseDate;
    }
}
