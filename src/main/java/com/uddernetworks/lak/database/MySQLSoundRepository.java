package com.uddernetworks.lak.database;

import com.uddernetworks.lak.keys.DefaultKey;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.sounds.DefaultSoundVariant;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;
import com.uddernetworks.lak.sounds.modulation.SoundModulationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.uddernetworks.lak.Utility.getBytesFromUUID;
import static com.uddernetworks.lak.Utility.getUUIDFromBytes;
import static com.uddernetworks.lak.database.DatabaseUtility.preparedExecute;

@Component("mySQLSoundRepository")
public class MySQLSoundRepository implements SoundRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLSoundRepository.class);

    private final JdbcTemplate jdbc;
    private final SoundManager soundManager;
    private final SoundModulationFactory soundModulationFactory;

    public MySQLSoundRepository(JdbcTemplate jdbc,
                                @Qualifier("variableSoundManager") SoundManager soundManager,
                                @Qualifier("standardSoundModulationFactory") SoundModulationFactory soundModulationFactory) {
        this.jdbc = jdbc;
        this.soundManager = soundManager;
        this.soundModulationFactory = soundModulationFactory;
    }

    @Override
    public void init() throws IOException {
        LOGGER.info("Creating necessary tables...");
        jdbc.execute(readSQL("tables.sql"));

        soundManager.setSounds(jdbc.query("SELECT * FROM `sounds`;", (rs, index) ->
                new FileSound(getUUIDFromBytes(rs.getBytes("sound_id")), Path.of(rs.getString("path")))));

        soundManager.setVariants(jdbc.query("SELECT * FROM `sound_variants`;", (rs, index) ->
                new DefaultSoundVariant(getUUIDFromBytes(rs.getBytes("variant_id")),
                        soundManager.getSound(getUUIDFromBytes(rs.getBytes("sound_id"))).orElse(null),
                        rs.getString("description"),
                        new Color(Integer.parseInt(rs.getString("color"), 16)))));

        // It is not just added one by one, as adding individually may cause issues down the line depending on the
        // implementation of SoundVariant's handling of adding individual modulators.
        jdbc.query("SELECT * FROM `modulators`;", (rs, index) -> {
            var variantOptional = soundManager.getVariant(getUUIDFromBytes(rs.getBytes("variant_id")));
            if (variantOptional.isPresent()) {
                return soundModulationFactory.deserialize(variantOptional.get(), rs.getBytes("value"));
            }
            return Optional.<SoundModulation>empty();
        }).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(SoundModulation::getSoundVariant))
                .forEach(SoundVariant::setModulators);

        jdbc.query("SELECT * FROM `keys`;", (rs, index) -> {
            var keyId = rs.getInt("key");
            return new DefaultKey(KeyEnum.fromId(keyId).orElseThrow(() ->
                    new RuntimeException("Invalid KeyEnum ID " + keyId)), null, rs.getBoolean("loop"));
        });
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

    private String readSQL(String name) throws IOException {
        var resource = Objects.requireNonNull(getClass().getClassLoader().getResource("sql/" + name));

        try (var reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
