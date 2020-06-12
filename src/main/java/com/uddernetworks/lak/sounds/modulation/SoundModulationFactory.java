package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Creaes {@link SoundModulation}s from serialized data.
 */
public interface SoundModulationFactory {

    /**
     * Creates a {@link SoundModulation} from a given serialized long created by {@link SoundModulation#serialize()}
     *
     * @param soundVariant The {@link SoundVariant} parent
     * @param bytes        The serialized data
     * @return A created {@link SoundModulation}
     */
    Optional<SoundModulation> deserialize(SoundVariant soundVariant, byte[] bytes);

    /**
     * Creates a {@link SoundModulation} from a given endpoint-serialized string, created by a serialized
     * {@link SoundModulation#getSerializable()}.
     *
     * @param soundVariant The {@link SoundVariant} parent
     * @param data         The string JSON endpoint data
     * @return A created {@link SoundModulation}
     */
    Optional<SoundModulation> deserializeFromEndpoint(SoundVariant soundVariant, ModulationId modulationId, ModulatorData data);
}
