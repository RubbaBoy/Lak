package com.uddernetworks.lak.database.key;

import com.uddernetworks.lak.database.RepositoryInitializer;
import com.uddernetworks.lak.keys.DefaultKey;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Initializes data for the {@link KeyManager} and other related classes.
 */
@Component("keyInitializer")
public class KeyInitializer implements RepositoryInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyInitializer.class);

    private final JdbcTemplate jdbc;
    private final KeyManager keyManager;

    public KeyInitializer(JdbcTemplate jdbc,
                          @Qualifier("defaultKeyManager") KeyManager keyManager) {
        this.jdbc = jdbc;
        this.keyManager = keyManager;
    }

    @Override
    public void init() {
        LOGGER.info("Initializing keys");

        jdbc.query("SELECT * FROM `keys`;", (rs, index) -> {
            var keyId = rs.getInt("key");
            return new DefaultKey(KeyEnum.fromId(keyId).orElseThrow(() ->
                    new RuntimeException("Invalid KeyEnum ID " + keyId)), null, rs.getBoolean("loop"));
        });
    }
}
