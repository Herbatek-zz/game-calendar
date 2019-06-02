package com.piotrek.gamecalendar.security.oauth2.providers;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

class OAuth2UserInfoFactoryTest {

    private final Map<String, Object> attributes = new HashMap<>();

    @Test
    void getOAuth2UserInfo_whenGoogleLowerCase_thenGoogleOauth2UserInfo() {
        // given
        GoogleOAuth2UserInfo expectGoogle = new GoogleOAuth2UserInfo(attributes);

        // when
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(AuthProvider.google.toLowerCase(), attributes);
        // then
        then(oAuth2UserInfo)
                .isNotNull()
                .isEqualToComparingFieldByField(expectGoogle);
    }

    @Test
    void getOAuth2UserInfo_whenGoogleUpperCase_thenGoogleOauth2UserInfo() {
        // given
        GoogleOAuth2UserInfo expectGoogle = new GoogleOAuth2UserInfo(attributes);

        // when
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(AuthProvider.google.toUpperCase(), attributes);
        // then
        then(oAuth2UserInfo)
                .isNotNull()
                .isEqualToComparingFieldByField(expectGoogle);
    }

    @Test
    void getOAuth2UserInfo_whenFacebookLowerCase_thenFacebookOauth2UserInfo() {
        // given
        FacebookOAuth2UserInfo expectFacebook = new FacebookOAuth2UserInfo(attributes);

        // when
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(AuthProvider.facebook.toLowerCase(), attributes);

        // then
        then(oAuth2UserInfo)
                .isNotNull()
                .isEqualToComparingFieldByField(expectFacebook);
    }

    @Test
    void getOAuth2UserInfo_whenFacebookUpperCase_thenFacebookOauth2UserInfo() {
        // given
        FacebookOAuth2UserInfo expectFacebook = new FacebookOAuth2UserInfo(attributes);

        // when
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(AuthProvider.facebook.toUpperCase(), attributes);

        // then
        then(oAuth2UserInfo)
                .isNotNull()
                .isEqualToComparingFieldByField(expectFacebook);
    }

    @Test
    void getOAuth2UserInfo_whenOther_thenOAuth2AuthenticationProcessingException() {
        // given
        final String registrationId = "other";

        // when
        final Throwable throwable = catchThrowable(() -> OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes));

        // then
        then(throwable).hasMessage("Sorry! Login with " + registrationId + " is not supported yet.");
    }
}