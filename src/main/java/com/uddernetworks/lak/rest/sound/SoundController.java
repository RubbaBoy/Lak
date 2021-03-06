package com.uddernetworks.lak.rest.sound;

import com.uddernetworks.lak.rest.exceptions.ModulatorUpdateException;
import com.uddernetworks.lak.rest.exceptions.SoundNotFoundException;
import com.uddernetworks.lak.rest.exceptions.SoundVariantNotFoundException;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.input.Recording;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/sounds")
public class SoundController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundController.class);

    private final SoundManager soundManager;
    private final ModulationManager modulationManager;
    private final Recording recording;

    public SoundController(@Qualifier("variableSoundManager") SoundManager soundManager,
                           @Qualifier("defaultModulationManager") ModulationManager modulationManager,
                           @Qualifier("webButtonRecording") Recording recording) {
        this.soundManager = soundManager;
        this.modulationManager = modulationManager;
        this.recording = recording;
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

    @PostMapping(path = "/recordSound", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Map<String, String> recordSound(@RequestBody SoundEndpointBodies.RecordingSound recordingSound) {
        recording.prepareRecording(recordingSound.getName());
        return Map.of("status", "ok");
    }

    @PostMapping(path = "/uploadSound")
    public @ResponseBody
    Map<String, String> uploadSound(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) throws IOException {
        if (file.getOriginalFilename() == null) {
            LOGGER.error("Couldn't get file name!");
            return Map.of("status", "not ok");
        }

        var name = new File(file.getOriginalFilename()).getName();

        LOGGER.debug("Saving sound {}", name);

        var saving = soundManager.convertSoundPath(name);
        Files.write(saving, file.getBytes());

        var sound = new FileSound(UUID.randomUUID(), name);
        soundManager.addSound(sound);
        soundManager.addSoundVariant("Default", sound);

        return Map.of("status", "ok");
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
