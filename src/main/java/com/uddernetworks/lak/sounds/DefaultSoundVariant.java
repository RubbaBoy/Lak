package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.sounds.modulation.ModulationId;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Stored in the sound_variant table
 */
public class DefaultSoundVariant implements SoundVariant {

    private final UUID id;
    private Sound sound;
    private String description;
    private Color color;
    private final List<SoundModulation> soundModulators = new ArrayList<>();

    private DefaultSoundVariant() {
        id = null;
    }

    public DefaultSoundVariant(UUID id, Sound sound) {
        this(id, sound, "", new Color(0));
    }

    public DefaultSoundVariant(UUID id, Sound sound, String description, Color color) {
        this.id = id;
        this.sound = sound;
        this.description = description;
        this.color = color;
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
    public void setModulators(List<SoundModulation> soundModulators) {
        this.soundModulators.clear();
        this.soundModulators.addAll(soundModulators);
    }

    @Override
    public void addModulator(SoundModulation soundModulation) {
        soundModulators.add(soundModulation);
    }

    @Override
    public void removeModulator(ModulationId modulationId) {
        soundModulators.removeIf(modulator -> modulator.getId() == modulationId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultSoundVariant that = (DefaultSoundVariant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DefaultSoundVariant{" +
                "id=" + id +
                ", sound=" + sound +
                ", description='" + description + '\'' +
                ", color=" + color +
                ", soundModulators=" + soundModulators +
                '}';
    }
}
