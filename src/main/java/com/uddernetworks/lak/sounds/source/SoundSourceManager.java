package com.uddernetworks.lak.sounds.source;

import com.jsyn.data.AudioSample;
import com.uddernetworks.lak.sounds.SoundVariant;

import java.util.Optional;

/**
 * A manager to handle the creation and cleanup of sound {@link AudioSample}s.
 */
public interface SoundSourceManager {

    /**
     * Gets or creates a {@link AudioSample} for the given {@link SoundVariant}.
     *
     * @param soundVariant The {@link SoundVariant} to create
     * @return The created or retrieved {@link AudioSample}. The Optional may be empty if an error occurs while loading
     *          the file.
     */
    Optional<AudioSample> getOrCreate(SoundVariant soundVariant);

}
