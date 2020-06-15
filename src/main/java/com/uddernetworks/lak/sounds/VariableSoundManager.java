package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.database.sound.SoundRepository;
import com.uddernetworks.lak.rest.sound.SoundEndpointBodies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.uddernetworks.lak.database.DatabaseUtility.waitFuture;

@Component("variableSoundManager")
public class VariableSoundManager implements SoundManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VariableSoundManager.class);

    @Value("${lak.database.synchronous}")
    private boolean synchronous;

    private final SoundRepository soundRepository;
    private final List<Sound> sounds = new ArrayList<>();
    private final List<SoundVariant> soundVariants = new ArrayList<>();

    public VariableSoundManager(@Qualifier("sqlSoundRepository") SoundRepository soundRepository) {
        this.soundRepository = soundRepository;
    }

    @Override
    public List<SoundVariant> getAllSoundVariants() {
        return soundVariants;
    }

    @Override
    public List<Sound> getAllSounds() {
        return sounds;
    }

    @Override
    public boolean isSoundAdded(UUID soundUUID) {
        return getSound(soundUUID).isPresent();
    }

    @Override
    public boolean isSoundVariantAdded(UUID variantUUID) {
        return getVariant(variantUUID).isPresent();
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
            waitFuture(synchronous, soundRepository.removeSound(soundUUID));
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
        waitFuture(synchronous, soundRepository.addSound(sound));
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
        if (!isSoundAdded(sound.getId())) {
            addSound(sound);
        }

        var variant = new DefaultSoundVariant(UUID.randomUUID(), sound);
        soundVariants.add(variant);
        waitFuture(synchronous, soundRepository.addVariant(variant));
        return variant;
    }

    @Override
    public void removeSoundVariant(SoundVariant sound) {
        removeSoundVariant(sound.getId());
    }

    @Override
    public void removeSoundVariant(UUID variantUUID) {
        if (soundVariants.removeIf(variant -> variant.getId().equals(variantUUID))) {
            waitFuture(synchronous, soundRepository.removeVariant(variantUUID));
        }
    }

    @Override
    public void updateVariant(SoundEndpointBodies.UpdatingVariant updatingVariant) {
        var storedVariantOptional = soundVariants.stream().filter(variant -> variant.getId().equals(updatingVariant.getId())).findFirst();
        if (storedVariantOptional.isPresent()) {
            var storedVariant = storedVariantOptional.get();

            var soundId = updatingVariant.getSoundId();
            if (soundId != null) {
                sounds.stream().filter(sound -> sound.getId().equals(updatingVariant.getSoundId()))
                        .findFirst()
                        .ifPresent(storedVariant::setSound);
            }

            var description = updatingVariant.getDescription();
            if (description != null) {
                storedVariant.setDescription(description);
            }

            var color = updatingVariant.getColor();
            if (color != null) {
                storedVariant.setColor(color);
            }

            waitFuture(synchronous, soundRepository.updateVariant(storedVariant));
        }
    }
}
