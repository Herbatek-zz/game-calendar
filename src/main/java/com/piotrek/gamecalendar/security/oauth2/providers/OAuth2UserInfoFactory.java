package com.piotrek.gamecalendar.security.oauth2.providers;

import com.piotrek.gamecalendar.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        switch (registrationId.toUpperCase()) {
            case AuthProvider.google:
                return new GoogleOAuth2UserInfo(attributes);
            case AuthProvider.facebook:
                return new FacebookOAuth2UserInfo(attributes);
            default:
                throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}