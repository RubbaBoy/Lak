package com.uddernetworks.lak.sounds.source;

import com.jsyn.data.AudioSample;
import com.jsyn.util.SampleLoader;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component("cachedSoundSourceManager")
public class CachedSoundSourceManager implements SoundSourceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedSoundSourceManager.class);

    private final Map<UUID, AudioSample> sources = new ConcurrentHashMap<>();
    private final SoundManager soundManager;

    public CachedSoundSourceManager(@Qualifier("variableSoundManager") SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    @Override
    public Optional<AudioSample> getOrCreate(SoundVariant soundVariant) {
        return Optional.ofNullable(sources.computeIfAbsent(soundVariant.getId(), $ -> {
            try {
                var file = soundManager.convertSoundPath(soundVariant.getSound().getRelativePath()).toFile();
                return SampleLoader.loadFloatSample(file);
            } catch (IOException e) {
                LOGGER.error("An error occurred while trying to load the audio file for " + soundVariant, e);
                return null;
            }
        }));
    }

}
