package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;

import java.util.Optional;

/**
 * Creaes {@link SoundModulation}s from serialized data.
 */
public interface SoundModulationFactory {

    /**
     * Creates a {@link SoundModulation} from a given serialized long created by {@link SoundModulation#serialize()}
     *
     * @param bytes The serialized data
     * @return A created {@link SoundModulation}
     */
    Optional<SoundModulation> deserialize(SoundVariant soundVariant, byte[] bytes);
}
