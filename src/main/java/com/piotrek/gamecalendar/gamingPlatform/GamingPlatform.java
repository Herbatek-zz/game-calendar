package com.piotrek.gamecalendar.gamingPlatform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GamingPlatform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate releaseDate;

    public GamingPlatform(String name) {
        this.name = name;
    }

    public GamingPlatform(String name, LocalDate releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }
}
