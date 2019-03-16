package com.piotrek.gamecalendar.gameGenre;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GameGenre {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
