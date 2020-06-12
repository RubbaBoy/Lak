package com.uddernetworks.lak.database;

import com.uddernetworks.lak.keys.DefaultKey;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.modulation.SoundModulationFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KeyInitializer {
//
//    private final JdbcTemplate jdbc;
//    private final KeyManager keyManager;
//
//    public KeyInitializer(JdbcTemplate jdbc, KeyManager keyManager) {
//        this.jdbc = jdbc;
//        this.keyManager = keyManager;
//    }
//
//    @PostConstruct
//    public void init() {
//        jdbc.query("SELECT * FROM keys;", (rs, index) -> {
//            var keyId = rs.getInt("key");
//            return new DefaultKey(KeyEnum.fromId(keyId).orElseThrow(() ->
//                    new RuntimeException("Invalid KeyEnum ID " + keyId)), null, rs.getBoolean("loop"));
//        });
//    }
//
}
