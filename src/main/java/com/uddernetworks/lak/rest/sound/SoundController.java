package com.uddernetworks.lak.rest.sound;

import com.uddernetworks.lak.rest.exceptions.ModulatorUpdateException;
import com.uddernetworks.lak.rest.exceptions.SoundNotFoundException;
import com.uddernetworks.lak.rest.exceptions.SoundVariantNotFoundException;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.modulation.ModulationManager;
import com.uddernetworks.lak.sounds.modulation.ModulatorData;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/sounds")
public class SoundController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundController.class);

    private final SoundManager soundManager;
    private final ModulationManager modulationManager;

    public SoundController(@Qualifier("variableSoundManager") SoundManager soundManager,
                           @Qualifier("defaultModulationManager") ModulationManager modulationManager) {
        this.soundManager = soundManager;
        this.modulationManager = modulationManager;
    }

    @GetMapping(path = "/list")
    public @ResponseBody
    Iterable<Sound> getAllSound() {
        return soundManager.getAllSounds();
    }

    @GetMapping(path = "/listVariants")
    public @ResponseBody
    Iterable<SoundVariant> getAllSoundVariants() {
        return soundManager.getAllSoundVariants();
    }

    @PostMapping(path = "/addSound", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Sound addSound(@RequestBody SoundEndpointBodies.AddingSound addingSound) {
        var sound = new FileSound(UUID.randomUUID(), addingSound.getRelPath());
        soundManager.addSound(sound);
        return sound;
    }

    @PostMapping(path = "/addVariant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    SoundVariant addVariant(@RequestBody SoundEndpointBodies.AddingVariant addingVariant) {
        var sound = soundManager.getSound(addingVariant.getSoundId()).orElseThrow(() ->
                new SoundNotFoundException(addingVariant.getSoundId()));
        return soundManager.addSoundVariant(addingVariant.getName(), sound);
    }

    @PostMapping(path = "/updateVariant", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, Object> updateVariant(@RequestBody SoundEndpointBodies.UpdatingVariant updatingVariant) {
        if (!soundManager.isSoundVariantAdded(updatingVariant.getId())) {
            throw new SoundVariantNotFoundException(updatingVariant.getId());
        }

        if (updatingVariant.getSoundId() != null && !soundManager.isSoundAdded(updatingVariant.getSoundId())) {
            throw new SoundNotFoundException(updatingVariant.getSoundId());
        }

        soundManager.updateVariant(updatingVariant);

        return Map.of("status", "ok");
    }

    @PostMapping(path = "/addModulator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> addModulator(@RequestBody SoundEndpointBodies.AddRemoveModulator addingModulator) {
        var variantUUID = addingModulator.getVariantId();

        var soundVariantOptional = soundManager.getVariant(variantUUID);

        if (soundVariantOptional.isEmpty()) {
            throw new SoundVariantNotFoundException(variantUUID);
        }

        modulationManager.addOrModifyModulator(soundVariantOptional.get(), addingModulator.getId(), new ModulatorData());

        return Map.of("status", "ok");
    }

    @PostMapping(path = "/removeModulator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, Object> removeModulator(@RequestBody SoundEndpointBodies.AddRemoveModulator removingModulator) {
        var variantUUID = removingModulator.getVariantId();

        var soundVariantOptional = soundManager.getVariant(variantUUID);

        if (soundVariantOptional.isEmpty()) {
            throw new SoundVariantNotFoundException(variantUUID);
        }

        modulationManager.removeModulator(soundVariantOptional.get(), removingModulator.getId());

        return Map.of("status", "ok");
    }

    @PostMapping(path = "/updateModulator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    SoundModulation updateModulator(@RequestBody SoundEndpointBodies.UpdatingModulator updatingModulator) {
        var variantUUID = updatingModulator.getVariantId();

        var soundVariantOptional = soundManager.getVariant(variantUUID);

        if (soundVariantOptional.isEmpty()) {
            throw new SoundVariantNotFoundException(variantUUID);
        }

        return modulationManager.addOrModifyModulator(soundVariantOptional.get(), updatingModulator.getId(), updatingModulator.getModulatorData())
                .orElseThrow(() -> new ModulatorUpdateException(updatingModulator.getModulatorData()));
    }
}
