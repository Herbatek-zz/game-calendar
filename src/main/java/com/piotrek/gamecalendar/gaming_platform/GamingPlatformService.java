package com.piotrek.gamecalendar.gaming_platform;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GamingPlatformService {

    private final GamingPlatformRepository gamingPlatformRepository;

    Page<GamingPlatform> findPageOfGamingPlatforms(Pageable pageable) {
//        return gamingPlatformRepository.findAll(pageable);
        return null;
    }

    Page<GamingPlatform> findPageOfGamesByName(String name, Pageable pageable) {
        return null;
    }

    GamingPlatform findById(Long id) {
        return null;
    }

    GamingPlatform save(GamingPlatform gamingPlatform) {
        return null;
    }

    GamingPlatform deleteById(Long id) {
        return null;
    }
}
