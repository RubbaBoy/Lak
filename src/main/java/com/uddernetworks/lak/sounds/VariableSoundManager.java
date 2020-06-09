package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.database.SoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("variableSoundManager")
public class VariableSoundManager implements SoundManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VariableSoundManager.class);

    private final SoundRepository soundRepository;
    private final List<Sound> sounds = new ArrayList<>();
    private final List<SoundVariant> soundVariants = new ArrayList<>();

    public VariableSoundManager(@Qualifier("mySQLSoundRepository") SoundRepository soundRepository) {
        this.soundRepository = soundRepository;
    }

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
    public void removeSound(Sound sound) {
        removeSound(sound.getId());
    }

    @Override
    public void removeSound(UUID soundUUID) {
        if (sounds.removeIf(sound -> sound.getId().equals(soundUUID))) {
            soundRepository.removeSound(soundUUID);
        }
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
        soundRepository.addSound(sound);
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

        var variant = new DefaultSoundVariant(UUID.randomUUID(), sound);
        soundRepository.addVariant(variant);
        return variant;
    }

    @Override
    public void removeSoundVariant(SoundVariant sound) {
        removeSoundVariant(sound.getId());
    }

    @Override
    public void removeSoundVariant(UUID variantUUID) {
        if (soundVariants.removeIf(variant -> variant.getId().equals(variantUUID))) {
            soundRepository.removeVariant(variantUUID);
        }
    }

    @Override
    public void updateVariant(SoundVariant soundVariant) {
        soundVariants.removeIf(variant -> variant.getId().equals(soundVariant.getId()));
        soundVariants.add(soundVariant);
        soundRepository.updateVariant(soundVariant);
    }
}
