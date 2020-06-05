package com.uddernetworks.lak.rest;

import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundVariant;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(path = "/sounds")
public class SoundEndpoints {

    @GetMapping(path = "/list")
    public @ResponseBody
    Iterable<SoundVariant> getAllSounds() {
        return null;
    }

    @PostMapping(path = "/addSound", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Sound addSound(@RequestParam AddingSound addingSound) {
        // TODO: Update soundVariant
        return null;
    }

    @PostMapping(path = "/addVariant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    SoundVariant addVariant(AddingVariant addingVariant) {
        // TODO: Update soundVariant
        return null;
    }

    @PostMapping(path = "/updateVariant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> updateVariant(SoundVariant soundVariant) {
        // TODO: Update soundVariant
        return Map.of("status", "ok");
    }

    static class AddingSound {
        private String name;

        public String getName() {
            return name;
        }
    }

    static class AddingVariant {
        private String name;
        private UUID soundId;

        public String getName() {
            return name;
        }

        public UUID getSoundId() {
            return soundId;
        }
    }
}
