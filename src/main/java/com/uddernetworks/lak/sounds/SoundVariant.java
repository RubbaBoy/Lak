package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.sounds.modulation.SoundModulation;

import java.awt.Color;
import java.util.List;
import java.util.UUID;

/**
 * A mutable object storing a {@link Sound} and additional data to it, such as name or mutations.
 * This allows for single copies of the raw {@link Sound} while allowing for duplicates or moficiations.
 */
public interface SoundVariant {

    /**
     * Gets the UUID of the {@link SoundVariant}.
     *
     * @return The UUID
     */
    UUID getId();

    /**
     * Gets the core, unmodulated {@link Sound}.
     *
     * @return The {@link Sound}
     */
    Sound getSound();

    /**
     * Gets the {@link SoundVariant}'s description.
     *
     * @return The {@link SoundVariant}'s description.
     */
    String getDescription();

    /**
     * Sets the {@link SoundVariant}'s description.
     *
     * @param description The {@link SoundVariant}'s description.
     */
    void setDescription(String description);

    /**
     * Gets the color of the {@link SoundVariant}.
     *
     * @return The color
     */
    Color getColor();

    /**
     * Sets the color for the {@link SoundVariant}.
     *
     * @param color The color to set
     */
    void setColor(Color color);

    /**
     * Gets the used {@link SoundModulation}s.
     *
     * @return An immutable list of {@link SoundModulation}s.
     */
    List<SoundModulation> getModulators();

    /**
     * Adds the given {@link SoundModulation}. If one already exists of the same type, it is overridden.
     *
     * @param soundModulation The {@link SoundModulation} to add
     */
    void addModulator(SoundModulation soundModulation);
}
