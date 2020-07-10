package com.uddernetworks.lak.sounds.input;

import com.uddernetworks.lak.sounds.Sound;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface Recording {

    /**
     * Quickly flashes the blue button LED and allows it to be pressed for a recording.
     * @param name The name of the sound
     * @return The recorded {@link Sound} after completion
     */
    CompletableFuture<Sound> prepareRecording(String name);
}
