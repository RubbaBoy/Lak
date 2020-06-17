package com.uddernetworks.lak.database;

import com.uddernetworks.lak.database.key.KeyRepository;
import com.uddernetworks.lak.database.sound.SoundInitializer;
import com.uddernetworks.lak.database.sound.SoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.IOException;

import static com.uddernetworks.lak.Utility.readResourceString;

@Component
public class SQLInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQLInitializer.class);

    private final JdbcTemplate jdbc;
    private final RepositoryInitializer soundInitializer;
    private final RepositoryInitializer keyInitializer;

    public SQLInitializer(JdbcTemplate jdbc,
                          @Qualifier("soundInitializer") RepositoryInitializer soundInitializer,
                          @Qualifier("keyInitializer") RepositoryInitializer keyInitializer) {
        this.jdbc = jdbc;
        this.soundInitializer = soundInitializer;
        this.keyInitializer = keyInitializer;
    }

    @PostConstruct
    private void init() throws IOException {
        LOGGER.debug("Setting MySQL syntax...");
        jdbc.execute("SET DATABASE SQL SYNTAX MYS TRUE");

        LOGGER.debug("Creating tables...");
        jdbc.execute(readResourceString("sql/tables.sql"));

//        jdbc.execute("TRUNCATE SCHEMA PUBLIC AND COMMIT;");

        LOGGER.debug("Initializing sounds...");
        soundInitializer.init();

        LOGGER.debug("Initializing keys...");
        keyInitializer.init();
    }
}
