package com.piotrek.gamecalendar.gameReleaseDate;

import com.piotrek.gamecalendar.gamingPlatform.GamingPlatform;
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
}
