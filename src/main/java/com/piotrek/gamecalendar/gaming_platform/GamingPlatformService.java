package com.piotrek.gamecalendar.gaming_platform;

import com.piotrek.gamecalendar.exceptions.NotFoundException;
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
        return gamingPlatformRepository.findAll(pageable);
    }

    GamingPlatform findById(Long id) {
        return gamingPlatformRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Gaming Platform with id = " + id));
    }

    GamingPlatform save(GamingPlatform gamingPlatform) {
        return gamingPlatformRepository.save(gamingPlatform);
    }

    GamingPlatform deleteById(Long id) {
        GamingPlatform toDelete = findById(id);
        gamingPlatformRepository.delete(toDelete);
        return toDelete;
    }
}
