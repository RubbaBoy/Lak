package com.uddernetworks.lak.sounds.modulation;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * A modulator to change the pitch of a sound by changing the sample rate
 */
public class PitchModulation implements SoundModulation {

    // The sample rate of the sound
    private float pitch = 0;

    @Override
    public ModulationId getId() {
        return ModulationId.PITCH;
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
}
