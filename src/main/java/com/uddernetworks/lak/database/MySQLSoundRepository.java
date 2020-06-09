package com.uddernetworks.lak.database;

import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.uddernetworks.lak.Utility.getBytesFromUUID;
import static com.uddernetworks.lak.Utility.readResource;
import static com.uddernetworks.lak.Utility.readResourceString;
import static com.uddernetworks.lak.database.DatabaseUtility.preparedExecute;

@Component("mySQLSoundRepository")
public class MySQLSoundRepository implements SoundRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLSoundRepository.class);

    private final JdbcTemplate jdbc;

    public MySQLSoundRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        jdbc.execute("SET DATABASE SQL SYNTAX MYS TRUE");

        LOGGER.debug("Creating tables...");
        jdbc.execute(readResourceString("sql/tables.sql"));
    }

    @Override
    public CompletableFuture<Void> addSound(Sound sound) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("INSERT INTO `sounds` VALUES (?, ?);", preparedExecute(stmt -> {
                    stmt.setBytes(1, getBytesFromUUID(sound.getId()));
                    stmt.setString(1, sound.getPath().toString());
                })));
    }

    @Override
    public CompletableFuture<Void> removeSound(UUID soundUUID) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("DELETE FROM `sounds` WHERE sound_id = ?;", preparedExecute(stmt ->
                        stmt.setBytes(1, getBytesFromUUID(soundUUID)))));
    }

    @Override
    public CompletableFuture<Void> addVariant(SoundVariant soundVariant) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("INSERT INTO `sound_variants` VALUES (?, ?, ?, ?);", preparedExecute(stmt -> {
                    stmt.setBytes(1, getBytesFromUUID(soundVariant.getId()));
                    stmt.setString(2, soundVariant.getDescription());
                    stmt.setString(3, Integer.toString(soundVariant.getColor().getRGB(), 16));
                    stmt.setBytes(4, getBytesFromUUID(soundVariant.getSound().getId()));
                })));
    }

    @Override
    public CompletableFuture<Void> removeVariant(UUID variantUUID) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("DELETE FROM `sound_variants` WHERE sound_id = ?;", preparedExecute(stmt ->
                        stmt.setBytes(1, getBytesFromUUID(variantUUID)))));
    }

    @Override
    public CompletableFuture<Void> updateVariant(SoundVariant soundVariant) {
        return CompletableFuture.runAsync(() -> jdbc.execute("UPDATE `sound_variants` SET description = ?, color = ?, sound_id = ? WHERE variant_id = ?;", preparedExecute(stmt -> {
            stmt.setString(1, soundVariant.getDescription());
            stmt.setString(2, Integer.toString(soundVariant.getColor().getRGB(), 16));
            stmt.setBytes(3, getBytesFromUUID(soundVariant.getSound().getId()));
            stmt.setBytes(4, getBytesFromUUID(soundVariant.getId()));
        })));
    }
}
