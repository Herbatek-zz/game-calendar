package com.piotrek.gamecalendar.gameSeries;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GameSeries {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
