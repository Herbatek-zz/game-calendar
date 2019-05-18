package com.piotrek.gamecalendar.util;

public interface ApplicationConstraints {
    // pagination
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "20";
    int MAX_PAGE_SIZE = 100;

    // oauth2
    String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    int COOKIE_EXPIRE_SECONDS = 180;
}
