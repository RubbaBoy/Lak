package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.sounds.source.SoundSourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("auxSoundPlayer")
public class AuxSoundPlayer implements SoundPlayer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuxSoundPlayer.class);

    private final KeyManager keyManager;
    private final SoundManager soundManager;
    private final SoundSourceManager soundSourceManager;

    public AuxSoundPlayer(@Qualifier("defaultKeyManager") KeyManager keyManager,
                          @Qualifier("variableSoundManager") SoundManager soundManager,
                          @Qualifier("cachedSoundSourceManager") SoundSourceManager soundSourceManager) {
        this.keyManager = keyManager;
        this.soundManager = soundManager;
        this.soundSourceManager = soundSourceManager;
    }

    @Override
    public void playSound(KeyEnum keyEnum) {
        try {
            var key = keyManager.getKeyFrom(keyEnum);
            var soundVariant = key.getSound();
            var sound = soundVariant.getSound();

            LOGGER.debug("Playing sound {}", sound.getId());

            var sourceOptional = soundSourceManager.getOrCreate(soundVariant);
            if (sourceOptional.isEmpty()) {
                LOGGER.error("Sound for {} unavailable, skipping", soundVariant);
                return;
            }

            var source = sourceOptional.get();

            source.rewind();

            for (var modulator : soundVariant.getModulators()) {
                LOGGER.debug("Modding with {}", modulator);
                modulator.modulateSound(source);
            }

            source.play();
        } catch (Exception e) {
            LOGGER.error("An error has occurred while playing a sound for key " + keyEnum, e);
        }
    }
}
