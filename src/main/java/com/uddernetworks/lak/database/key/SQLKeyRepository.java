package com.uddernetworks.lak.database.key;

import com.uddernetworks.lak.keys.Key;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static com.uddernetworks.lak.database.DatabaseUtility.executeArgs;

@Component("sqlKeyRepository")
public class SQLKeyRepository implements KeyRepository {

    private final JdbcTemplate jdbc;

    public SQLKeyRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public CompletableFuture<Void> updateKey(Key key) {
        return CompletableFuture.runAsync(() ->
                jdbc.execute("UPDATE `keys` SET `variant_id` = ?, `loop` = ? WHERE `key` = ? AND `shift` = ?;", executeArgs(
                        key.getSound().getId(),
                        key.isLoop(),
                        key.getKey().getId(),
                        key.getKey().isShift()
                )));
    }
}
