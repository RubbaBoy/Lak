package com.uddernetworks.lak.sounds.modulation;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

/**
 * A modulator to change the pitch of a sound.
 */
public class VolumeModulation implements SoundModulation {

    // The change in volume of the sound in decibels
    private float volume = 0;

    @Override
    public ModulationId getId() {
        return ModulationId.VOLUME;
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
}