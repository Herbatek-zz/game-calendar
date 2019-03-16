package com.piotrek.gamecalendar.game;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
