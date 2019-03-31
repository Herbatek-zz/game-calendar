package com.piotrek.gamecalendar.security.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String token;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }
}
