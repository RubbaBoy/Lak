package com.uddernetworks.lak.sounds.modulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uddernetworks.lak.sounds.SoundVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.urish.openal.ALException;
import org.urish.openal.Source;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;

import static com.uddernetworks.lak.Utility.clamp;
import static com.uddernetworks.lak.Utility.copyBuffer;

/**
 * A modulator to change the pitch of a sound.
 */
public class VolumeModulation extends SoundModulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeModulation.class);

    @JsonIgnore
    private final SoundVariant soundVariant;

    // The change in volume of the sound from 0.0 - ?
    private double volume = 0;

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
     * <br>Range: [0.0 - ?]
     * <br>Default: 1
     *
     * @return The volume of the sound
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the sound.
     * <br>Range: [0.0 - ?]
     * <br>Default: 1
     *
     * @param volume The volume of the sound to set
     * @return The current {@link PitchModulation}
     */
    public VolumeModulation setVolume(double volume) {
        this.volume = clamp(volume, 0, 1000000);
        return this;
    }

    @Override
    public void updateFromEndpoint(ModulatorData data) {
        volume = clamp(data.<Double>get("volume", 1D), 0, 1000000);
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
    public void modulateSound(Source source) throws ALException {
        source.setGain((float) volume);
    }

    @Override
    public byte[] serialize() {
        var output = copyBuffer(super.serialize(), 32);
        output.putFloat((float) volume);
        return output.array();
    }

    public static VolumeModulation deserialize(SoundVariant soundVariant, ByteBuffer buffer) {
        var modulation = new VolumeModulation(soundVariant);
        modulation.volume = buffer.getFloat();
        return modulation;
    }
}
