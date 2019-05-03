package com.piotrek.gamecalendar.user.dto;

import com.piotrek.gamecalendar.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .imageUrl(imageUrl).build();
    }
}


