package com.uddernetworks.lak.sounds.modulation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.VariableRateDataReader;
import com.uddernetworks.lak.sounds.SoundVariant;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * An interface to hold modulations to be made to sounds, such as pitch or volume, depending on the implementation.
 */
public abstract class SoundModulation {

    /**
     * Gets the {@link ModulationId} defined for the {@link SoundModulation} implementation.
     *
     * @return The {@link ModulationId}
     */
    abstract public ModulationId getId();

    /**
     * Gets the {@link SoundVariant} that is bound to the current {@link SoundModulation}. This is final.
     *
     * @return The {@link SoundVariant}
     */
    abstract public SoundVariant getSoundVariant();

    /**
     * Updates the current {@link SoundModulation} with the given properties data provided by a source such as a REST
     * endpoint. This is simple a map to keep it generic as possible, and decrease unnecessary class dependencies.
     *
     * @param data The properties data to update the {@link SoundModulation} from. See relevant implementation
     *             for details on what is acceptable. Invalid/unused keys are disregarded.
     */
    abstract public void updateFromEndpoint(ModulatorData data);

    /**
     * Gets data to be serialized in the form of a {@link ModulatorData}.
     *
     * @return THe {@link ModulatorData} to be serialized
     */
    public abstract ModulatorData getSerializable();

    /**
     * Modulates the given sound with the current modulation settings.
     *
     * @param synth The {@link Synthesizer}
     * @param player The {@link VariableRateDataReader}
     */
    abstract public void modulateSound(Synthesizer synth, VariableRateDataReader player);

    /**
     * Serialize the modulation so it can be inserted into the database.
     * It is expected that the first byte of data represents the {@link ModulationId#getId()} value.
     * The remaining bytes are up for the modulation implementation to create and resolve.
     *
     * @return The serialized Long
     */
    public byte[] serialize() {
        var output = ByteBuffer.allocate(1);
        output.put(getId().getId());
        return output.array();
    }
}
