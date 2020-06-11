package com.uddernetworks.lak.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.awt.Color;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
class ColorCombinedSerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uriSerializer() throws JsonProcessingException {
        // Reads the AARRGGBB
        var color = objectMapper.readValue("\"AABBCCDD\"", Color.class);
        assertEquals(170, color.getAlpha());
        assertEquals(187, color.getRed());
        assertEquals(204, color.getGreen());
        assertEquals(221, color.getBlue());
    }

    @Test
    void uriDeserializer() throws JsonProcessingException {
        var color = objectMapper.writeValueAsString(new Color(187, 204, 221, 170));
        assertEquals("\"AABBCCDD\"", color);
    }

}
