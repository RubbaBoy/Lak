package com.uddernetworks.lak.rest;

import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.modulation.SoundModulationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(path = "/sounds")
public class SoundController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundController.class);

    private final SoundManager soundManager;
    private final SoundModulationFactory soundModulationFactory;

    public SoundController(@Qualifier("variableSoundManager") SoundManager soundManager,
                           @Qualifier("standardSoundModulationFactory") SoundModulationFactory soundModulationFactory) {
        this.soundManager = soundManager;
        this.soundModulationFactory = soundModulationFactory;
    }

    @GetMapping(path = "/list")
    public @ResponseBody
    Iterable<Sound> getAllSound() {
        LOGGER.info("LISTINGGGGGG");
        return soundManager.getAllSounds();
    }

    @GetMapping(path = "/listVariants")
    public @ResponseBody
    Iterable<SoundVariant> getAllSoundVariants() {
        return soundManager.getAllSoundVariants();
    }

    @PostMapping(path = "/addSound", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Sound addSound(@RequestBody AddingSound addingSound) {
        LOGGER.error("Bruh moment ading sound");
        var sound = new FileSound(UUID.randomUUID(), URI.create(addingSound.getURI()));
        soundManager.addSound(sound);
        System.out.println("sound = " + sound);
        return sound;
    }

    @PostMapping(path = "/addVariant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    SoundVariant addVariant(@RequestBody AddingVariant addingVariant) {
        var sound = soundManager.getSound(addingVariant.getSoundId()).orElseThrow(() ->
                new SoundNotFoundException(addingVariant.getSoundId()));
        return soundManager.addSoundVariant(sound);
    }

    @PostMapping(path = "/updateVariant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> updateVariant(@RequestBody SoundVariant soundVariant) {
        // TODO: Update soundVariant
        return Map.of("status", "ok");
    }

    static class AddingSound {
        private String uri;

        public String getURI() {
            return uri;
        }

        public void setURI(String uri) {
            this.uri = uri;
        }

        @Override
        public String toString() {
            return "AddingSound{" +
                    "uri='" + uri + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "AddingVariant{" +
                    "name='" + name + '\'' +
                    ", soundId=" + soundId +
                    '}';
        }
    }
}
