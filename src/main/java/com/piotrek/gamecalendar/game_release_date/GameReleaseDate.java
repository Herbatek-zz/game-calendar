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

    @OneToOne
    @Transient
    private GamingPlatform gamingPlatform;
    private LocalDate releaseDate;

    public GameReleaseDate(GamingPlatform gamingPlatform, LocalDate releseDate) {
        this.gamingPlatform = gamingPlatform;
        this.releaseDate = releseDate;
    }
}
