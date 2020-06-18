package com.uddernetworks.lak.sounds.source;

import com.uddernetworks.lak.sounds.SoundVariant;
import org.urish.openal.ALException;
import org.urish.openal.Source;

import java.util.Optional;

/**
 * A manager to handle the creation and cleanup of sound {@link Source}s.
 */
public interface SoundSourceManager {

    /**
     * Gets or creates a {@link Source} for the given {@link SoundVariant}.
     *
     * @param soundVariant The {@link SoundVariant} to create
     * @return The created or retrieved {@link Source}. The Optional may be empty if an error occurs while loading the
     *         file.
     */
    Optional<Source> getOrCreate(SoundVariant soundVariant);

}
