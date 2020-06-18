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
 * A modulator to change the pitch of a sound by changing the sample rate
 */
public class PitchModulation extends SoundModulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(PitchModulation.class);

    @JsonIgnore
    private final SoundVariant soundVariant;

    // The sample rate of the sound from 0.5 - 2
    private double pitch = 0;

    public PitchModulation(SoundVariant soundVariant) {
        this.soundVariant = soundVariant;
    }

    @Override
    public ModulationId getId() {
        return ModulationId.PITCH;
    }

    @Override
    public SoundVariant getSoundVariant() {
        return soundVariant;
    }

    /**
     * Sets the pitch of the sound.
     * <br>Range: [0.5 - 2]
     * <br>Default: 1
     *
     * @return The pitch of the sound
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * Sets the pitch of the sound.
     * <br>Range: [0.5 - 2]
     * <br>Default: 1
     *
     * @param pitch The pitch of the sound to set
     * @return The current {@link PitchModulation}
     */
    public PitchModulation setPitch(double pitch) {
        this.pitch = clamp(pitch, 0.5, 2);
        return this;
    }

    @Override
    public void updateFromEndpoint(ModulatorData data) {
        pitch = clamp(data.<Double>get("pitch", 1D), 0.5, 2);
    }

    @Override
    public ModulatorData getSerializable() {
        return new ModulatorData(Map.of("pitch", pitch));
    }

    public static PitchModulation fromEndpointString(SoundVariant soundVariant, ModulatorData data) {
        var pitchModulation = new PitchModulation(soundVariant);
        pitchModulation.updateFromEndpoint(data);
        return pitchModulation;
    }

    @Override
    public void modulateSound(Source source) throws ALException {
        source.setPitch((float) pitch);
    }

    @Override
    public byte[] serialize() {
        var output = copyBuffer(super.serialize(), 32);
        output.putFloat((float) pitch);
        return output.array();
    }

    public static PitchModulation deserialize(SoundVariant soundVariant, ByteBuffer buffer) {
        var modulation = new PitchModulation(soundVariant);
        modulation.pitch = buffer.getFloat();
        return modulation;
    }
}
