package com.piotrek.gamecalendar.achievement;

import lombok.Data;

@Data
public class AchievementDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double percentUsersAchieved;
}