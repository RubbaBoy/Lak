package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.nio.ByteBuffer;

import static com.uddernetworks.lak.Utility.copyBuffer;

/**
 * A modulator to change the pitch of a sound by changing the sample rate
 */
public class PitchModulation extends SoundModulation {

    private final SoundVariant soundVariant;
    // The sample rate of the sound
    private float pitch = 0;

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

    @Override
    public void updateFromEndpoint(ModulatorData data) {
        pitch = data.get("pitch");
    }

    @Override
    public AudioFormat modulateSound(AudioFormat audioFormat, Clip clip) {
        var control = (FloatControl) clip.getControl(FloatControl.Type.SAMPLE_RATE);
        control.setValue(pitch);
        return null;
    }

    @Override
    public byte[] serialize() {
        var output = copyBuffer(super.serialize(), 32);
        output.putFloat(pitch);
        return output.array();
    }

    public static PitchModulation deserialize(SoundVariant soundVariant, ByteBuffer buffer) {
        var modulation = new PitchModulation(soundVariant);
        modulation.pitch = buffer.getFloat();
        return modulation;
    }
}
