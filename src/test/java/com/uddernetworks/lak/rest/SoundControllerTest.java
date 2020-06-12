package com.uddernetworks.lak.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uddernetworks.lak.ResultList;
import com.uddernetworks.lak.Utility;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.modulation.ModulationId;
import com.uddernetworks.lak.sounds.modulation.VolumeModulation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import java.awt.Color;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.uddernetworks.lak.Utility.args;
import static com.uddernetworks.lak.Utility.preparedArgs;
import static com.uddernetworks.lak.database.DatabaseUtility.queryArgs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void resetSounds() {
        // Removes all sounds and variants. Paired with this class being transactional, this ensures a clean
        // SoundManager and database to work with each test in this class.
        LOGGER.warn("Before each!");
        soundManager.getAllSounds().clear();
        soundManager.getAllSoundVariants().clear();
    }

    @Test
    void getAllSounds() throws JsonProcessingException {
        // Add 5 random sound UUIDs to the database
        var uuids = IntStream.range(0, 5)
                .mapToObj($ -> UUID.randomUUID())
                .peek(this::addDatabaseSound)
                .collect(Collectors.toList());

        var soundType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Sound.class);

        // List all sounds from the testing endpoint
        var result = objectMapper.<List<Sound>>readValue(get("/list"), soundType);

        assertEquals(result.size(), 5);

        for (var sound : result) {
            // Ensure the set ID and URI match the set values
            var id = sound.getId();
            var uri = sound.getURI();

            assertTrue(uuids.contains(id));
            assertEquals(SOUND_URI, uri);
        }
    }

    @Test
    void getAllSoundVariants() throws JsonProcessingException {
        var soundUUID = UUID.randomUUID();

        var sound = addDatabaseSound(soundUUID);

        // Add 5 variants to the SoundManager, keeping track of their generated UUIDs
        var uuids = IntStream.range(0, 5)
                .mapToObj($ -> sound)
                .map(this::addDatabaseSoundVariant)
                .map(SoundVariant::getId)
                .collect(Collectors.toList());

        var soundVariantType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, SoundVariant.class);

        // List all sounds from the testing endpoint
        var result = objectMapper.<List<SoundVariant>>readValue(get("/listVariants"), soundVariantType);

        assertEquals(result.size(), 5);

        for (var soundVariant : result) {
            // Ensure the variant ID matches, and the sound ID that was set previously does as well
            var id = soundVariant.getId();
            var jsonSoundUUID = soundVariant.getSound().getId();

            assertTrue(uuids.contains(id));
            assertEquals(soundUUID, jsonSoundUUID);
        }
    }

    @Test
    void addSound() throws JsonProcessingException {
        // Make the request to the testing endpoint
        var created = objectMapper.readValue(post("addSound", Map.of("uri", SOUND_URI)), Sound.class);

        // Get the returned data from the request
        var soundUUID = created.getId();
        var path = created.getURI();

        var result = jdbc.query("SELECT * FROM sounds WHERE sound_id = ?;", queryArgs(soundUUID), ResultList::new);

//        // Ensure validity of data
        assertNotNull(result);
        assertTrue(result.hasNext());
        result.next();
        assertEquals(soundUUID, Utility.getUUIDFromBytes(result.get(0)));
        assertEquals(path.toString(), result.get(1));
    }

    @Test
    void addVariant() throws JsonProcessingException {
        // Add a random sound UUID to the SoundManager manually
        var soundUUID = UUID.randomUUID();

        var sound = addDatabaseSound(soundUUID);

        // Make a request to the testing endpoint
        var variant = objectMapper.readValue(post("addVariant", Map.of("soundId", soundUUID)), SoundVariant.class);

        // Get the returned data from the request
        var fetchedSound = variant.getSound();
        var jsonUUID = fetchedSound.getId();
        var jsonURI = fetchedSound.getURI();

        // Ensure it was actually created
        assertTrue(soundManager.isSoundVariantAdded(variant.getId()));

        // Ensure it matches the data started off with/manually added
        assertEquals(soundUUID, jsonUUID);
        assertEquals(sound.getURI(), jsonURI);
    }

    @Test
    void updateVariant() throws JsonProcessingException {
        // Add a random sound UUID to the SoundManager manually
        var firstSound = UUID.randomUUID();
        var secondSound = UUID.randomUUID();

        var first = addDatabaseSound(firstSound);
        var second = addDatabaseSound(secondSound);
        var variant = addDatabaseSoundVariant(first);

        LOGGER.warn("This variant hash: {}", variant.hashCode());

        // Make a request to the testing endpoint
        // Only the description and color are being updated, to ensure the sound stays the same
        var description = "Some desc";
        var color = Color.RED;
        var json = objectMapper.readValue(post("updateVariant", Map.of("id", variant.getId(), "description", description, "color", color)), Map.class);
        assertEquals("ok", json.get("status"));
        assertEquals(firstSound, variant.getSound().getId());
        assertEquals(description, variant.getDescription());
        assertEquals(color, variant.getColor());

        // Send another request to ensure only the sound is being updated
        var secondJson = objectMapper.readValue(post("updateVariant", Map.of("id", variant.getId(), "soundId", secondSound)), Map.class);

        assertEquals("ok", json.get("status"));
        assertEquals(secondSound, variant.getSound().getId());
        assertEquals(description, variant.getDescription());
        assertEquals(color, variant.getColor());

    }

    @Test
    void addModulator() throws JsonProcessingException {
        var soundUUID = UUID.randomUUID();

        var sound = addDatabaseSound(soundUUID);
        var variant = addDatabaseSoundVariant(sound);

        var json = objectMapper.readValue(post("addModulator", Map.of("variantUUID", variant.getId(), "id", ModulationId.VOLUME.getId())), Map.class);

        System.out.println("json = " + json);

        var modulators = variant.getModulators();

        assertEquals(1, modulators.size());
        assertEquals("ok", json.get("status"));
    }

    @Test
    void removeModulator() {

    }

    @Test
    void updateModulator() {

    }

    private Sound addDatabaseSound(UUID soundUUID) {
        var sound = new FileSound(soundUUID, SOUND_URI);
        soundManager.addSound(sound);
        return sound;
    }

    private SoundVariant addDatabaseSoundVariant(Sound sound) {
        return soundManager.addSoundVariant(sound);
    }

    private String get(String path) {
        return restTemplate.getForObject("http://localhost:" + port + "/sounds/" + path, String.class);
    }

    private String post(String path, Map<String, Object> json) throws JsonProcessingException {
        var posting = objectMapper.writeValueAsString(json);
        return post(path, posting);
    }

    private String post(String path, String json) {
        return restTemplate.postForObject("http://localhost:" + port + "/sounds/" + path, new HttpEntity<>(json, headers), String.class);
    }

}
