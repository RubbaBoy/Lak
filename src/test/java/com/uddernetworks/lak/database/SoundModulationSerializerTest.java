package com.uddernetworks.lak.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uddernetworks.lak.sounds.FileSound;
import com.uddernetworks.lak.sounds.Sound;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.SoundVariant;
import com.uddernetworks.lak.sounds.modulation.ModulationId;
import com.uddernetworks.lak.sounds.modulation.ModulatorData;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;
import com.uddernetworks.lak.sounds.modulation.VolumeModulation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SoundModulationSerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("variableSoundManager")
    private SoundManager soundManager;

    @Test
    void soundModulationSerializer() throws JsonProcessingException {
        var sound = addDatabaseSound(UUID.randomUUID());
        var variant = addDatabaseSoundVariant(sound);

        var volume = new VolumeModulation(variant);
        volume.setVolume(0.45);

        var path = objectMapper.writeValueAsString(volume);
        assertEquals("{\"id\":" + ModulationId.VOLUME.getId() + ",\"variant\":\"" + variant.getId() + "\",\"value\":{\"volume\":0.45}}", path);
    }

    @Test
    void soundModulationDeserializer() throws JsonProcessingException {
        var sound = addDatabaseSound(UUID.randomUUID());
        var variant = addDatabaseSoundVariant(sound);

        var modulation = objectMapper.readValue("{\"id\":" + ModulationId.VOLUME.getId() + ",\"variant\":\"" + variant.getId() + "\",\"value\":{\"volume\":0.45}}", SoundModulation.class);

        assertTrue(modulation instanceof VolumeModulation);

        var volumeModulation = (VolumeModulation) modulation;

        assertEquals(ModulationId.VOLUME, volumeModulation.getId());
        assertEquals(variant.getId(), volumeModulation.getSoundVariant().getId());
        assertEquals(0.45, volumeModulation.getVolume());
    }

    private Sound addDatabaseSound(UUID soundUUID) {
        var sound = new FileSound(soundUUID, URI.create(""));
        soundManager.addSound(sound);
        return sound;
    }

    private SoundVariant addDatabaseSoundVariant(Sound sound) {
        return soundManager.addSoundVariant(sound);
    }

}
