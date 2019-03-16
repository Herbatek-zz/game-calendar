package com.piotrek.gamecalendar.gameEngine;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GameEngine {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
