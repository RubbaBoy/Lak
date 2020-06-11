package com.uddernetworks.lak.rest;

import com.uddernetworks.lak.ResultList;
import com.uddernetworks.lak.Utility;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

//import javax.transaction.Transactional;
import javax.transaction.Transactional;
import java.net.URI;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.uddernetworks.lak.database.DatabaseUtility.preparedExecute;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SoundControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundControllerTest.class);

    private static final URI SOUND_URI = URI.create("/path/here.mp3");

    private static HttpHeaders headers;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    @Qualifier("variableSoundManager")
    private SoundManager soundManager;

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void getAllSounds() throws JSONException {
        // Add 5 random sound UUIDs to the database
        var uuids = IntStream.range(0, 5)
                .mapToObj($ -> UUID.randomUUID())
                .peek(this::addDatabaseSound)
                .collect(Collectors.toList());

        // List all sounds from the testing endpoint
        var result = new JSONArray(get("/list"));

        assertTrue(result.length() > 0);

        for (int i = 0; i < result.length(); i++) {
            var sound = result.getJSONObject(i);
            var id = UUID.fromString(sound.getString("id"));
            var uri = URI.create(sound.getString("uri"));

            assertTrue(uuids.contains(id));
            assertEquals(SOUND_URI, uri);
        }
    }

    @Test
    void getAllSoundVariants() {
        var soundUUID = UUID.randomUUID();

        var sound = addDatabaseSound(soundUUID);
        var variant = addDatabaseSoundVariant(sound);


    }

    @Test
    void addSound() throws JSONException {
        // Make the request to the testing endpoint
        var created = new JSONObject(post("addSound", Map.of("uri", SOUND_URI)));

        // Get the returned data from the request
        var soundUUID = UUID.fromString(created.getString("id"));
        var path = created.getString("uri");

        // Select the data that should have been added
        var result = jdbc.query("SELECT * FROM `sounds`", ResultList::new);

        // Ensure validity of data
        assertNotNull(result);
        assertArrayEquals(Utility.getBytesFromUUID(soundUUID), result.get(0));
        assertEquals(path, result.get(1));
    }

    @Test
    void addVariant() throws JSONException {
        var soundUUID = UUID.randomUUID();

        addDatabaseSound(soundUUID);

        var json = new JSONObject(post("addVariant", Map.of("name", "something", "soundId", soundUUID)));
        System.out.println("json = " + json);
//        var expected = new JSONObject(Map.of("id", soundUUID));
//        assertEquals(json.getString("id"), expected);
    }

    @Test
    void updateVariant() {

    }

    private Sound addDatabaseSound(UUID soundUUID) {
        var sound = new FileSound(soundUUID, SOUND_URI);
        soundManager.addSound(sound);
        return sound;
    }

    private SoundVariant addDatabaseSoundVariant(Sound sound) {
        return soundManager.addSoundVariant(sound);
    }

    private String get(String path) throws JSONException {
        return restTemplate.getForObject("http://localhost:" + port + "/sounds/" + path, String.class);
    }

    private String post(String path, Map<String, Object> json) throws JSONException {
        return post(path, new JSONObject(json).toString());
    }

    private String post(String path, String json) throws JSONException {
        return restTemplate.postForObject("http://localhost:" + port + "/sounds/" + path, new HttpEntity<>(json, headers), String.class);
    }

}
