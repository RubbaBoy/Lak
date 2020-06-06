package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.sounds.modulation.SoundModulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Stored in the `sound_variant` table
 */
public class DefaultSoundVariant implements SoundVariant {

    private UUID id;
    private Sound sound;
    private String description;
    private Color color;
    private final List<SoundModulation> soundModulators = new ArrayList<>();

    public DefaultSoundVariant(UUID id, Sound sound) {
        this.id = id;
        this.sound = sound;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Sound getSound() {
        return sound;
    }

    @Override
    public void setSound(Sound sound) {
        this.sound = sound;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public List<SoundModulation> getModulators() {
        return Collections.unmodifiableList(soundModulators);
    }

    @Override
    public void addModulator(SoundModulation soundModulation) {
        soundModulators.add(soundModulation);
    }
}
