package com.piotrek.gamecalendar.user.helpers;

import com.piotrek.gamecalendar.role.Role;
import com.piotrek.gamecalendar.role.RoleName;
import com.piotrek.gamecalendar.security.payload.SignUpRequest;
import com.piotrek.gamecalendar.user.User;

import java.util.Set;

public class CustomTestModels {

    private final static String TEST123_USERNAME = "test123";
    private final static String TEST123_EMAIL = "test123@gmail.com";
    private final static String TEST123_PASSWORD = "123456";

    public static SignUpRequest createTest123SignUpRequest() {
        var signUpRequest = new com.piotrek.gamecalendar.security.payload.SignUpRequest();
        signUpRequest.setUsername(TEST123_USERNAME);
        signUpRequest.setEmail(TEST123_EMAIL);
        signUpRequest.setPassword(TEST123_PASSWORD);
        return signUpRequest;
    }

    public static User createTest123User() {
        return User.builder()
                .id(123L)
                .username(TEST123_USERNAME)
                .email(TEST123_EMAIL)
                .roles(Set.of(Role.builder().name(RoleName.ROLE_USER).build()))
                .build();
    }
}
