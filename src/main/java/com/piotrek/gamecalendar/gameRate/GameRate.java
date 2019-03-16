package com.piotrek.gamecalendar.gameRate;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GameRate {

    @Id
    @GeneratedValue
    private Long id;

    private int rate;
}
