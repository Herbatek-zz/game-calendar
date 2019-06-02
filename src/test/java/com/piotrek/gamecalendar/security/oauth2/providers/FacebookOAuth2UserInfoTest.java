package com.piotrek.gamecalendar.security.oauth2.providers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class FacebookOAuth2UserInfoTest {

    private final static Map<String, Object> attributes = new HashMap<>();
    private static FacebookOAuth2UserInfo facebookOAuth2UserInfo;

    @BeforeAll
    static void beforeAll() {
        attributes.put("id", "1234");
        attributes.put("name", "Johnnie");
        attributes.put("email", "johnnie@gmail.com");

        Map<String, Object> data = new HashMap<>();
        data.put("url", "https://johnnie-images.com/img1.png");

        Map<String, Object> picture = new HashMap<>();
        picture.put("data", data);
        attributes.put("picture", picture);

        facebookOAuth2UserInfo =  new FacebookOAuth2UserInfo(attributes);
    }

    @Test
    void getId() {
        // given
        final String expectedId = "1234";

        // when
        final String id = facebookOAuth2UserInfo.getId();

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
        final String name = facebookOAuth2UserInfo.getName();

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
        final String email = facebookOAuth2UserInfo.getEmail();

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
        final String imageUrl = facebookOAuth2UserInfo.getImageUrl();

        // then
        then(imageUrl)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedImageUrl);
    }
}