package com.uddernetworks.lak.sounds.source;

import com.uddernetworks.lak.sounds.SoundVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.urish.openal.ALException;
import org.urish.openal.OpenAL;
import org.urish.openal.Source;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component("cachedSoundSourceManager")
public class CachedSoundSourceManager implements SoundSourceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedSoundSourceManager.class);

    private final OpenAL openAL;
    private final Map<UUID, Source> sources = new ConcurrentHashMap<>();

    @Autowired
    public CachedSoundSourceManager(OpenAL openAL) {
        this.openAL = openAL;
    }

    @Override
    public Optional<Source> getOrCreate(SoundVariant soundVariant) {
        return Optional.ofNullable(sources.computeIfAbsent(soundVariant.getId(), $ -> {
            try {
                return openAL.createSource(soundVariant.getSound().getURI().toURL());
            } catch (ALException | UnsupportedAudioFileException | IOException e) {
                LOGGER.error("An error occurred while trying to load the audio file for " + soundVariant, e);
                return null;
            }
        }));
    }

}
