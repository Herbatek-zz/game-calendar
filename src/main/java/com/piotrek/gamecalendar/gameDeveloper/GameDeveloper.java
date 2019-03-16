package com.piotrek.gamecalendar.gameDeveloper;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GameDeveloper {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
