package com.uddernetworks.lak.sounds.modulation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uddernetworks.lak.sounds.SoundVariant;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.nio.ByteBuffer;
import java.util.Map;

import static com.uddernetworks.lak.Utility.copyBuffer;

/**
 * A modulator to change the pitch of a sound by changing the sample rate
 */
public class PitchModulation extends SoundModulation {

    @JsonIgnore
    private final SoundVariant soundVariant;

    // The sample rate of the sound
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

    public double getPitch() {
        return pitch;
    }

    public PitchModulation setPitch(double pitch) {
        this.pitch = pitch;
        return this;
    }

    @Override
    public void updateFromEndpoint(ModulatorData data) {
        pitch = data.<Double>get("pitch", 0D);
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
    public AudioFormat modulateSound(AudioFormat audioFormat, Clip clip) {
        var control = (FloatControl) clip.getControl(FloatControl.Type.SAMPLE_RATE);
        control.setValue((float) pitch);
        return null;
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
