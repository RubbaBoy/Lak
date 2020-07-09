package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.keys.DefaultKey;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.sounds.modulation.PitchModulation;
import com.uddernetworks.lak.sounds.modulation.VolumeModulation;
import com.uddernetworks.lak.sounds.source.CachedSoundSourceManager;
import com.uddernetworks.lak.sounds.source.SoundSourceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuxSoundPlayerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuxSoundPlayerTest.class);

    // The duration in milliseconds of the test sound being played
    private static final long SOUND_DURATION = 6500;

    @Mock
    private SoundManager soundManager;

    @Mock
    private KeyManager keyManager;

    @Spy
    private final SoundSourceManager soundSourceManager = new CachedSoundSourceManager();

    @InjectMocks
    private AuxSoundPlayer soundPlayer;

    @Value("${lak.test.sounds.playSound}")
    private boolean playSound;

    void sleep(double millis) {
        if (playSound) {
            try {
                Thread.sleep((long) millis);
            } catch (InterruptedException ignored) {}
        }
    }

    @BeforeEach
    public void init() {
        var soundVariant = new DefaultSoundVariant(UUID.randomUUID(), new FileSound(UUID.randomUUID(), new File("src/test/resources/sounds/Hol_After.wav").toURI()));

        LOGGER.info("Playing sounds: {}", playSound);

        when(soundManager.getAllSoundVariants()).thenReturn(List.of(soundVariant));
        when(keyManager.getKeyFrom(any(KeyEnum.class))).thenReturn(new DefaultKey(KeyEnum.RESERVED, soundVariant, false));
    }

    @Test
    public void pitchModulation() {
        LOGGER.info("Playing normal sound...");
        soundPlayer.playSound(KeyEnum.KEY_0);

        sleep(SOUND_DURATION);

        var variant = soundManager.getAllSoundVariants().get(0);
        variant.addModulator(new PitchModulation(variant).setPitch(1.5));

        if (playSound) {
            LOGGER.info("Playing pitch-shifted sound...");
            soundPlayer.playSound(KeyEnum.KEY_0);
        }

        sleep(SOUND_DURATION * 0.75);
    }

    @Test
    public void volumeModulation() {
        LOGGER.info("Playing normal sound...");
        soundPlayer.playSound(KeyEnum.KEY_0);

        sleep(SOUND_DURATION);

        var variant = soundManager.getAllSoundVariants().get(0);
        variant.addModulator(new VolumeModulation(variant).setVolume(0.25));

        if (playSound) {
            LOGGER.info("Playing sound at 25% volume...");
            soundPlayer.playSound(KeyEnum.KEY_0);
        }

        sleep(SOUND_DURATION);
    }

}
