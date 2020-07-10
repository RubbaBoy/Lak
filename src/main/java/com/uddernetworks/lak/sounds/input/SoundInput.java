package com.uddernetworks.lak.sounds.input;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

public interface SoundInput {

    /**
     * Starts recording a sound with the given name.
     *
     * @param name The name of the sound
     * @throws IOException If an exception occurs
     */
    void startRecording(String name) throws IOException;

    /**
     * Stops the recording of the sound and returns the {@link Path} of the file.
     *
     * @return The {@link Path} of the file
     * @throws IOException If an exception occurs
     */
    Path stopRecording() throws IOException;
}
