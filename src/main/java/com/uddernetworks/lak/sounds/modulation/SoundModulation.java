package com.uddernetworks.lak.sounds.modulation;

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
}
