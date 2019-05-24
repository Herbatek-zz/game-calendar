package com.piotrek.gamecalendar.gaming_platform;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gaming-platforms")
public class GamingPlatformController {

    private final GamingPlatformService gamingPlatformService;

    @GetMapping
    public Page<GamingPlatform> findPage(Pageable pageable) {
        return gamingPlatformService.findPageOfGamingPlatforms(pageable);
    }

    @GetMapping("/{id}")
    public GamingPlatform findById(@PathVariable Long id) {
        return gamingPlatformService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public GamingPlatform create(@Valid @RequestBody GamingPlatform gamingPlatform) {
        return gamingPlatformService.save(gamingPlatform);
    }

    @DeleteMapping("/{id}")
    public GamingPlatform delete(@PathVariable Long id) {
        return gamingPlatformService.deleteById(id);
    }

}
