package com.piotrek.gamecalendar.test_data;

import com.piotrek.gamecalendar.gaming_platform.GamingPlatform;
import lombok.Getter;

public class GamingPlatformTestObject {

    @Getter
    private GamingPlatform gamingPlatform;

    private GamingPlatformTestObject() {
        gamingPlatform = new GamingPlatform();
    }

    public static GamingPlatformTestObject builder() {
        return new GamingPlatformTestObject();
    }

    public GamingPlatformTestObject withId(Long id) {
        this.gamingPlatform.setId(id);
        return this;
    }

    public GamingPlatformTestObject pc() {
        gamingPlatform.setName("PC");
        return this;
    }

    public GamingPlatformTestObject xbox360() {
        gamingPlatform.setName("xbox");
        return this;
    }

    public GamingPlatformTestObject ps4() {
        gamingPlatform.setName("Playstation 4");
        return this;
    }

    public GamingPlatform build() {
        return this.gamingPlatform;
    }
}
