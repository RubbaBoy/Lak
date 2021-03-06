package com.uddernetworks.lak.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uddernetworks.lak.database.serializers.ColorSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.awt.Color;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
@ContextConfiguration(classes = ColorSerializer.class)
class ColorSoundModulationSerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uriSerializer() throws JsonProcessingException {
        // Reads the AARRGGBB
        var color = objectMapper.readValue("\"AABBCCDD\"", Color.class);
        assertEquals(187, color.getRed());
        assertEquals(204, color.getGreen());
        assertEquals(221, color.getBlue());
        assertEquals(170, color.getAlpha());
    }

    @Test
    void uriDeserializer() throws JsonProcessingException {
        var color = objectMapper.writeValueAsString(new Color(187, 204, 221, 170));
        assertEquals("\"AABBCCDD\"", color);
    }
}
