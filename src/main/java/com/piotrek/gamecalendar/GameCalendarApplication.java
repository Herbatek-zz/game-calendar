package com.piotrek.gamecalendar;

import com.piotrek.gamecalendar.security.oauth2.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(AuthProperties.class)
public class GameCalendarApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameCalendarApplication.class, args);
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
