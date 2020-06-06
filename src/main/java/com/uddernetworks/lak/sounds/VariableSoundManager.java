package com.uddernetworks.lak.sounds;

import java.util.List;
import java.util.UUID;

public class VariableSoundManager implements SoundManager {

    private List<Sound> sounds;
    private List<SoundVariant> soundVariants;

    @Override
    public List<SoundVariant> getAllSounds() {
        return soundVariants;
    }

    @Override
    public boolean isSoundAdded(Sound sound) {
        return sounds.contains(sound);
    }

    @Override
    public void addSound(Sound sound) {
        sounds.add(sound);
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
