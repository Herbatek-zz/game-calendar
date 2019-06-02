package com.piotrek.gamecalendar.security.oauth2.providers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class GoogleOAuth2UserInfoTest {

    private final static Map<String, Object> attributes = new HashMap<>();
    private static GoogleOAuth2UserInfo googleOAuth2UserInfo;

    @BeforeAll
    static void beforeAll() {
        attributes.put("sub", "1234");
        attributes.put("name", "Johnnie");
        attributes.put("email", "johnnie@gmail.com");
        attributes.put("picture", "https://johnnie-images.com/img1.png");

        googleOAuth2UserInfo =  new GoogleOAuth2UserInfo(attributes);
    }

    @Test
    void getId() {
        // given
        final String expectedId = "1234";

        // when
        String id = googleOAuth2UserInfo.getId();

        // then
        then(id)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedId);
    }

    @Test
    void getName() {
        // given
        final String expectedName = "Johnnie";

        // when
        String name = googleOAuth2UserInfo.getName();

        // then
        then(name)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedName);
    }

    @Test
    void getEmail() {
        // given
        final String expectedEmail = "johnnie@gmail.com";

        // when
        String email = googleOAuth2UserInfo.getEmail();

        // then
        then(email)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedEmail);
    }

    @Test
    void getImageUrl() {
        // given
        final String expectedImageUrl = "https://johnnie-images.com/img1.png";

        // when
        String imageUrl = googleOAuth2UserInfo.getImageUrl();

        // then
        then(imageUrl)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedImageUrl);
    }
}