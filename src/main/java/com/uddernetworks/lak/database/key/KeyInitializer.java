package com.uddernetworks.lak.database.key;

import com.uddernetworks.lak.database.RepositoryInitializer;
import com.uddernetworks.lak.keys.DefaultKey;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.sounds.SoundManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.uddernetworks.lak.Utility.getUUIDFromBytes;
import static com.uddernetworks.lak.database.DatabaseUtility.transformArgs;

/**
 * Initializes data for the {@link KeyManager} and other related classes.
 */
@Component("keyInitializer")
public class KeyInitializer implements RepositoryInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyInitializer.class);

    private final JdbcTemplate jdbc;
    private final KeyManager keyManager;
    private final SoundManager soundManager;

    public KeyInitializer(JdbcTemplate jdbc,
                          @Qualifier("defaultKeyManager") KeyManager keyManager,
                          @Qualifier("variableSoundManager") SoundManager soundManager) {
        this.jdbc = jdbc;
        this.keyManager = keyManager;
        this.soundManager = soundManager;
    }

    @Override
    public void init() {
        if (jdbc.query("SELECT * FROM `keys`;", ($, $2) -> null).isEmpty()) {
            LOGGER.debug("No keys in the database!");
            soundManager.getAllSoundVariants().stream().findAny().ifPresent(variant -> {
                LOGGER.debug("Adding keys with variant {}", variant.getId());

                jdbc.batchUpdate("INSERT INTO `keys` VALUES (?, ?, ?, ?)",
                        Arrays.stream(KeyEnum.values()).map(keyEnum ->
                                transformArgs(keyEnum.getId(), keyEnum.isShift(), variant.getId(), false))
                                .collect(Collectors.toList()));
            });
        }

        keyManager.setKeys(jdbc.query("SELECT * FROM `keys`;", (rs, index) -> {
            var keyId = rs.getInt("key");
            var shift = rs.getBoolean("shift");
            return new DefaultKey(
                    KeyEnum.fromId(keyId, shift).orElseThrow(() -> new RuntimeException("Invalid KeyEnum ID " + keyId)),
                    soundManager.getVariant(getUUIDFromBytes(rs.getBytes("variant_id"))).orElse(null),
                    rs.getBoolean("loop"));
        }));

        LOGGER.debug("Loaded {} keys (Out of {} possible)", keyManager.getAllKeys().size(), KeyEnum.values().length);
    }
}
