package com.uddernetworks.lak.sounds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("variableSoundManager")
public class VariableSoundManager implements SoundManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VariableSoundManager.class);

    private List<Sound> sounds = new ArrayList<>();
    private List<SoundVariant> soundVariants = new ArrayList<>();

    @Override
    public List<SoundVariant> getAllSounds() {
        return soundVariants;
    }

    @Override
    public boolean isSoundAdded(Sound sound) {
        return sounds.contains(sound);
    }

    @Override
    public Optional<Sound> getSound(UUID soundUUID) {
        return sounds.stream().filter(sound -> sound.getId().equals(soundUUID)).findFirst();
    }

    @Override
    public void setSounds(List<Sound> sounds) {
        if (!this.sounds.isEmpty()) {
            LOGGER.warn("Tried to invoke VariableSoundManager#setSounds(List<Sound>) while sounds list is populated");
            return;
        }

        this.sounds.addAll(sounds);
    }

    @Override
    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    @Override
    public Optional<SoundVariant> getVariant(UUID variantUUID) {
        return soundVariants.stream().filter(variant -> variant.getId().equals(variantUUID)).findFirst();
    }

    @Override
    public void setVariants(List<SoundVariant> variants) {
        if (!soundVariants.isEmpty()) {
            LOGGER.warn("Tried to invoke VariableSoundManager#setVariants(List<SoundVariant>) while sound variant list is populated");
            return;
        }

        this.soundVariants.addAll(variants);
    }

    @Override
    public SoundVariant addSoundVariant(Sound sound) {
        if (!isSoundAdded(sound)) {
            addSound(sound);
        }

        return new DefaultSoundVariant(UUID.randomUUID(), sound);
    }

    @Override
    public void updateVariant(SoundVariant soundVariant) {
        soundVariants.removeIf(variant -> variant.getId().equals(soundVariant.getId()));
        soundVariants.add(soundVariant);
    }
}
