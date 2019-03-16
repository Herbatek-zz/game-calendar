package com.piotrek.gamecalendar.gamingPlatform;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GamingPlatform {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
