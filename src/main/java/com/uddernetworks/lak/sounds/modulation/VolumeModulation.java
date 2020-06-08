package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.nio.ByteBuffer;

import static com.uddernetworks.lak.Utility.copyBuffer;

/**
 * A modulator to change the pitch of a sound.
 */
public class VolumeModulation extends SoundModulation {

    private final SoundVariant soundVariant;
    // The change in volume of the sound in decibels
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

    @Override
    public void updateFromEndpoint(ModulatorData data) {
        volume = data.get("volume");
    }

    @Override
    public AudioFormat modulateSound(AudioFormat audioFormat, Clip clip) {
        var control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(volume);
        return null;
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
