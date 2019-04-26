package com.piotrek.gamecalendar.security.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class SignUpRequest {

    @NotBlank
    @Size(min = 2, max = 15)
    private String username;

    @Email
    @NotBlank
    @Size(min = 6, max = 40)
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
