package com.uddernetworks.lak.sounds.modulation;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;

/**
 * An interface to hold modulations to be made to sounds, such as pitch or volume, depending on the implementation.
 */
public interface SoundModulation {

    /**
     * Gets the {@link ModulationId} defined for the {@link SoundModulation} implementation.
     *
     * @return The {@link ModulationId}
     */
    ModulationId getId();

    /**
     * Updates the current {@link SoundModulation} with the given properties data provided by a source such as a REST
     * endpoint. This is simple a map to keep it generic as possible, and decrease unnecessary class dependencies.
     *
     * @param data The properties data to update the {@link SoundModulation} from. See relevant implementation
     *             for details on what is acceptable. Invalid/unused keys are disregarded.
     */
    void updateFromEndpoint(ModulatorData data);

    /**
     * Modulates the given {@link AudioFormat} with the current modulation settings.
     *
     * @param audioFormat The {@link AudioFormat} to modulate
     * @param clip The {@link Clip} to be used if the implementation requires it
     * @return The modulated {@link AudioFormat}. If null, the {@link AudioFormat} is assumed to be unmodified
     */
    AudioFormat modulateSound(AudioFormat audioFormat, Clip clip);
}
