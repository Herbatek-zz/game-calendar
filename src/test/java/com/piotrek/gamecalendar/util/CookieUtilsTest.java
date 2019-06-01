package com.piotrek.gamecalendar.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.Cookie;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

class CookieUtilsTest {

    @Test
    @DisplayName("Should get cookie, when found cookie, then return cookie")
    void shouldGetCookie_whenFoundCookie_thenReturnCookie() {
        // given
        final Cookie wantedCookie = new Cookie("cookieName4", "cookieValue4");
        final Cookie[] cookies = {
                new Cookie("cookieName1", "cookieValue1"),
                new Cookie("cookieName2", "cookieValue2"),
                new Cookie("cookieName3", "cookieValue3"),
                wantedCookie
        };

        // when
        Optional<Cookie> cookieResult = CookieUtils.getCookie(cookies, wantedCookie.getName());

        // then
        then(cookieResult)
                .isNotEmpty()
                .get()
                .isEqualTo(wantedCookie);
    }

    @Test
    @DisplayName("Should get cookie, when not found cookie, then return optional empty")
    void shouldGetCookie_whenNotFoundCookie_thenReturnOptionalEmpty() {
        // given
        final Cookie wantedCookie = new Cookie("cookieName4", "cookieValue4");
        final Cookie[] cookies = {
                new Cookie("cookieName1", "cookieValue1"),
                new Cookie("cookieName2", "cookieValue2"),
                new Cookie("cookieName3", "cookieValue3"),
        };

        // when
        Optional<Cookie> cookieResult = CookieUtils.getCookie(cookies, wantedCookie.getName());

        // then
        then(cookieResult).isEmpty();
    }
}