package com.uddernetworks.lak.sounds;

import com.jsyn.util.SampleLoader;
import com.uddernetworks.lak.keys.DefaultKey;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.sounds.jsyn.CachedJSynPool;
import com.uddernetworks.lak.sounds.jsyn.JSynPool;
import com.uddernetworks.lak.sounds.modulation.PitchModulation;
import com.uddernetworks.lak.sounds.modulation.VolumeModulation;
import com.uddernetworks.lak.sounds.output.AuxSoundPlayer;
import com.uddernetworks.lak.sounds.source.CachedSoundSourceManager;
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
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuxSoundPlayerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuxSoundPlayerTest.class);

    // The duration in milliseconds of the test sound being played
    private static final long SOUND_DURATION = 6500;

    @Spy
    private JSynPool pool = new CachedJSynPool();

    @Mock
    private SoundManager soundManager;

    @Mock
    private KeyManager keyManager;

    @Mock
    private CachedSoundSourceManager soundSourceManager;

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
    public void init() throws IOException {
        var soundVariant = new DefaultSoundVariant(UUID.randomUUID(), new FileSound(UUID.randomUUID(), ""));

        LOGGER.info("Playing sounds: {}", playSound);

        when(soundSourceManager.getOrCreate(any(SoundVariant.class)))
                .thenReturn(Optional.ofNullable(SampleLoader.loadFloatSample(new File("src/test/resources/sounds/Hol_After.wav"))));

        when(soundManager.getAllSoundVariants()).thenReturn(List.of(soundVariant));
        when(keyManager.getKeyFrom(any(KeyEnum.class))).thenReturn(new DefaultKey(KeyEnum.RESERVED, soundVariant, false));
    }

    @Test
    public void pitchModulation() {
        LOGGER.info("Playing normal sound...");
        soundPlayer.playSound(KeyEnum.KEY_0);

        sleep(SOUND_DURATION);

        var variant = soundManager.getAllSoundVariants().get(0);
        variant.addModulator(new PitchModulation(variant).setPitch(1.5F));

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
        variant.addModulator(new VolumeModulation(variant).setVolume(0.25F));

        if (playSound) {
            LOGGER.info("Playing sound at 25% volume...");
            soundPlayer.playSound(KeyEnum.KEY_0);
        }

        sleep(SOUND_DURATION);
    }

}
