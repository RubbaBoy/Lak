package com.uddernetworks.lak.sounds.input;

import com.uddernetworks.lak.hardware.ButtonInterface;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component("webButtonRecording")
public class WebButtonRecording implements Recording {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebButtonRecording.class);
    private final SoundInput soundInput;
    private final ButtonInterface buttonInterface;
    private final SoundManager soundManager;

    // If true, the button is ready to record. If false, everything is done.
    private boolean recording;

    public WebButtonRecording(@Qualifier("auxSoundInput") SoundInput soundInput,
                              @Qualifier("defaultButtonInterface") ButtonInterface buttonInterface,
                              @Qualifier("variableSoundManager") SoundManager soundManager) {
        this.soundInput = soundInput;
        this.buttonInterface = buttonInterface;
        this.soundManager = soundManager;
    }

    @Override
    public synchronized CompletableFuture<Sound> prepareRecording(String name) {
        LOGGER.debug("Preparing recording");

        if (recording) {
            LOGGER.warn("Tried to record while already recording!");
            return CompletableFuture.completedFuture(null);
        }

        var completer = new CompletableFuture<Sound>();

        recording = true;
        buttonInterface.startRecording(() -> {
            try {
                LOGGER.debug("User pressed button, recording");
                soundInput.startRecording(name);
            } catch (IOException e) {
                LOGGER.error("An error occurred while starting the recording of '" + name + "'", e);
                completer.complete(null);
                recording = false;
            }
        }, () -> {
            LOGGER.debug("Recording done");
            recording = false;

            try {
                var soundPath = soundInput.stopRecording();
                LOGGER.info("Recorded sound at {}", soundPath);

                var sound = new FileSound(UUID.randomUUID(), soundPath);
                soundManager.addSound(sound);

                completer.complete(sound);
            } catch (IOException e) {
                LOGGER.error("An error occurred while stopping the recording of '" + name + "'", e);
                completer.complete(null);
                recording = false;
            }
        });

        return completer;
    }
}
