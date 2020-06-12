package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;

import java.util.Optional;

/**
 * Manages sound modulation from external inputs (e.g. the REST API)
 */
public interface ModulationManager {

    /**
     * Creates or modifies an existing a {@link SoundModulation} from the given {@link ModulatorData} to the
     * {@link SoundVariant}. Modifiers are only modified if another one of the same {@link ModulationId} exists.
     *
     * @param soundVariant The {@link SoundVariant} to add the modulator to
     * @param modulationId The {@link ModulationId} to identify the modulator being constructed
     * @param data         The {@link ModulatorData} containing relevant data to construct the modulator
     */
    Optional<SoundModulation> addOrModifyModulator(SoundVariant soundVariant, ModulationId modulationId, ModulatorData data);

    /**
     * Removes a given {@link SoundModulation} by its {@link ModulationId}, if present.
     *
     * @param soundVariant The {@link SoundVariant}
     * @param id The {@link ModulationId} to remove
     */
    void removeModulator(SoundVariant soundVariant, ModulationId id);
}
