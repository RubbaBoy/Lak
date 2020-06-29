package com.uddernetworks.lak.database.sound;

import com.uddernetworks.lak.database.RepositoryInitializer;
import com.uddernetworks.lak.sounds.DefaultSoundVariant;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;
import com.uddernetworks.lak.sounds.modulation.SoundModulationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.uddernetworks.lak.Utility.colorFromHex;
import static com.uddernetworks.lak.Utility.getUUIDFromBytes;

/**
 * Initializes the sound data for the database. This is not done in {@link SoundRepository} as to prevent cyclical
 * dependencies with {@link SoundManager}, and to keep that class specifically for accessing and updating tables.
 */
@Component("soundInitializer")
public class SoundInitializer implements RepositoryInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundInitializer.class);

    private final JdbcTemplate jdbc;
    private final SoundManager soundManager;
    private final SoundModulationFactory soundModulationFactory;

    public SoundInitializer(JdbcTemplate jdbc,
                            @Qualifier("variableSoundManager") SoundManager soundManager,
                            @Qualifier("standardSoundModulationFactory") SoundModulationFactory soundModulationFactory) {
        this.jdbc = jdbc;
        this.soundManager = soundManager;
        this.soundModulationFactory = soundModulationFactory;
    }

    @Override
    public void init() {
        LOGGER.debug("Initializing sounds");

        soundManager.setSounds(jdbc.query("SELECT * FROM `sounds`;", (rs, index) ->
                new FileSound(getUUIDFromBytes(rs.getBytes("sound_id")), URI.create(rs.getString("path")))));

        soundManager.setVariants(jdbc.query("SELECT * FROM `sound_variants`;", (rs, index) ->
                new DefaultSoundVariant(getUUIDFromBytes(rs.getBytes("variant_id")),
                        soundManager.getSound(getUUIDFromBytes(rs.getBytes("sound_id"))).orElse(null),
                        rs.getString("description"),
                        colorFromHex(rs.getString("color")))));

        // It is not just added one by one, as adding individually may cause issues down the line depending on the
        // implementation of SoundVariant's handling of adding individual modulators.
        jdbc.query("SELECT * FROM `modulators`;", (rs, index) -> {
            var variantOptional = soundManager.getVariant(getUUIDFromBytes(rs.getBytes("variant_id")));
            if (variantOptional.isPresent()) {
                return soundModulationFactory.deserialize(variantOptional.get(), rs.getBytes("value"));
            }
            return Optional.<SoundModulation>empty();
        }).stream()
                .flatMap(Optional::stream)
                .collect(Collectors.groupingBy(SoundModulation::getSoundVariant))
                .forEach(SoundVariant::setModulators);

        var soundSize = soundManager.getAllSounds().size();
        if (soundSize == 0) {
            LOGGER.debug("No sounds loaded, adding a default one");
            var sound = new FileSound(UUID.randomUUID(), Paths.get("E:/lak/sounds/Hol_After.wav").toUri());
            soundManager.addSound(sound);
            var variant = soundManager.addSoundVariant(sound);
            LOGGER.debug("Created sound {} and variant {}", sound.getId(), variant.getId());
        } else {
            LOGGER.debug("Loaded {} sound(s)", soundSize);
        }
    }
}
