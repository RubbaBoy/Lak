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
 * A modulator to change the pitch of a sound.
 */
public class VolumeModulation extends SoundModulation {

    @JsonIgnore
    private final SoundVariant soundVariant;

    // The change in volume of the sound in decibels
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

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public void updateFromEndpoint(ModulatorData data) {
        volume = data.get("volume");
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
    public AudioFormat modulateSound(AudioFormat audioFormat, Clip clip) {
        var control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue((float) volume);
        return null;
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
