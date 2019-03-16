package com.piotrek.gamecalendar.gamePublisher;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GamePublisher {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
