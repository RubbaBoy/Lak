package com.uddernetworks.lak.sounds.modulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.VariableRateDataReader;
import com.uddernetworks.lak.sounds.SoundVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;

import static com.uddernetworks.lak.Utility.clamp;
import static com.uddernetworks.lak.Utility.copyBuffer;
import static com.uddernetworks.lak.Utility.mapRange;

/**
 * A modulator to change the pitch of a sound.
 */
public class VolumeModulation extends SoundModulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeModulation.class);

    @JsonIgnore
    private final SoundVariant soundVariant;

    // The change in volume of the sound from 0.0 - 1.0
    private float volume = 0;

    public VolumeModulation(SoundVariant soundVariant) {
        this.soundVariant = soundVariant;
    }

    @Override
    public ModulationId getId() {
        return ModulationId.VOLUME;
    }

    @Override
    public SoundVariant getSoundVariant() {
        return soundVariant;
    }

    /**
     * Sets the volume of the sound.
     * <br>Range: [0.0, 1.0]
     * <br>Default: 1.0
     *
     * @return The volume of the sound
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the sound.
     * <br>Range: [0.0, 1.0]
     * <br>Default: 1.0
     *
     * @param volume The volume of the sound to set
     * @return The current {@link PitchModulation}
     */
    public VolumeModulation setVolume(float volume) {
        this.volume = clamp(volume, 0, 1);
        return this;
    }

    @Override
    public void updateFromEndpoint(ModulatorData data) {
        volume = clamp(data.<Number>get("volume", 1F).floatValue(), 0, 1);
    }

    @Override
    public ModulatorData getSerializable() {
        return new ModulatorData(Map.of("volume", volume));
    }

    public static VolumeModulation fromModularData(SoundVariant soundVariant, ModulatorData data) {
        var volumeModulation = new VolumeModulation(soundVariant);
        volumeModulation.updateFromEndpoint(data);
        return volumeModulation;
    }

    @Override
    public void modulateSound(Synthesizer synth, VariableRateDataReader player) {
        LOGGER.debug("Current amplitude {} range: [{}, {}]", player.amplitude.get(), player.amplitude.getMinimum(), player.amplitude.getMaximum());
        player.amplitude.set(mapRange(volume, 0, 1, player.amplitude.getMinimum(), player.amplitude.getMaximum()));
    }

    @Override
    public byte[] serialize() {
        var output = copyBuffer(super.serialize(), 32);
        output.putFloat(volume);
        return output.array();
    }

    public static VolumeModulation deserialize(SoundVariant soundVariant, ByteBuffer buffer) {
        var modulation = new VolumeModulation(soundVariant);
        modulation.volume = buffer.getFloat();
        return modulation;
    }
}
