package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;

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
     * @param data The {@link ModulatorData} containing relevant data to construct the modulator
     */
    void addOrModifyModulator(SoundVariant soundVariant, ModulationId modulationId, ModulatorData data);
}
