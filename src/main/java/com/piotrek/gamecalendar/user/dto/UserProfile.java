package com.piotrek.gamecalendar.user.dto;

import com.piotrek.gamecalendar.achievement.Achievement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private Set<Achievement> achievements;
}


