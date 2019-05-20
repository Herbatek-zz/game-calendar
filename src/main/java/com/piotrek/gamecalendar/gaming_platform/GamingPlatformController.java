package com.piotrek.gamecalendar.gaming_platform;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @GetMapping
    public Page<GamingPlatform> searchByName(@PathParam("name") String name, Pageable pageable) {
        return gamingPlatformService.findPageOfGamesByName(name, pageable);
    }

    @GetMapping("/{id}")
    public GamingPlatform findById(@PathVariable Long id) {
        return gamingPlatformService.findById(id);
    }

    @PostMapping
    public GamingPlatform create(@RequestBody GamingPlatform gamingPlatform) {
        return gamingPlatformService.save(gamingPlatform);
    }

    @DeleteMapping("/{id}")
    public GamingPlatform delete(@PathVariable Long id) {
        return gamingPlatformService.deleteById(id);
    }

}
