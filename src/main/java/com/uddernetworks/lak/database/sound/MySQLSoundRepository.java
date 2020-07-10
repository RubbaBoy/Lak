package com.uddernetworks.lak.database.sound;

import com.uddernetworks.lak.Utility;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
//import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.uddernetworks.lak.Utility.getBytesFromUUID;
import static com.uddernetworks.lak.Utility.hexFromColor;
import static com.uddernetworks.lak.Utility.readResourceString;
import static com.uddernetworks.lak.database.DatabaseUtility.executeArgs;

@Component("sqlSoundRepository")
public class MySQLSoundRepository implements SoundRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLSoundRepository.class);

    private final JdbcTemplate jdbc;

    public MySQLSoundRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
//    @Transactional
    public CompletableFuture<Void> addSound(Sound sound) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("INSERT INTO `sounds` VALUES (?, ?);", executeArgs(
                        Utility.getBytesFromUUID(sound.getId()),
                        sound.getRelativePath()
                )));
    }

    @Override
    public CompletableFuture<Void> removeSound(UUID soundUUID) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("DELETE FROM `sounds` WHERE `sound_id` = ?;", executeArgs(soundUUID)));
    }

    @Override
    public CompletableFuture<Void> addVariant(SoundVariant soundVariant) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("INSERT INTO `sound_variants` VALUES (?, ?, ?, ?);", executeArgs(
                        getBytesFromUUID(soundVariant.getId()),
                        soundVariant.getDescription(),
                        hexFromColor(soundVariant.getColor()),
                        soundVariant.getSound().getId()
                )));
    }

    @Override
    public CompletableFuture<Void> removeVariant(UUID variantUUID) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("DELETE FROM sound_variants WHERE `sound_id` = ?;", executeArgs(getBytesFromUUID(variantUUID))));
    }

    @Override
    public CompletableFuture<Void> updateVariant(SoundVariant soundVariant) {
        return CompletableFuture.runAsync(() -> jdbc.execute("UPDATE `sound_variants` SET `description` = ?, `color` = ?, `sound_id` = ? WHERE `variant_id` = ?;", executeArgs(
                soundVariant.getDescription(),
                hexFromColor(soundVariant.getColor()),
                soundVariant.getSound().getId(),
                soundVariant.getId()
        )));
    }

    @Override
    public CompletableFuture<Void> addModulator(SoundModulation soundModulation) {
        return CompletableFuture.runAsync(() -> jdbc.execute("INSERT INTO `modulators` VALUES (?, ?, ?);", executeArgs(
                soundModulation.getId().getId(),
                soundModulation.serialize(),
                soundModulation.getSoundVariant().getId()
        )));
    }

    @Override
    public CompletableFuture<Void> removeModulator(SoundModulation soundModulation) {
        return CompletableFuture.runAsync(() -> jdbc.execute("DELETE FROM `modulators` WHERE `modulation_id` = ? AND `variant_id` = ?", executeArgs(
                soundModulation.getId().getId(),
                soundModulation.getSoundVariant().getId()
        )));
    }

    @Override
    public CompletableFuture<Void> updateModulator(SoundModulation soundModulation) {
        return CompletableFuture.runAsync(() -> jdbc.execute("UPDATE `modulators` SET `value` = ? WHERE `modulation_id` = ? AND `variant_id` = ?;", executeArgs(
                soundModulation.serialize(),
                soundModulation.getId().getId(),
                soundModulation.getSoundVariant().getId()
        )));
    }
}
