package com.uddernetworks.lak.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uddernetworks.lak.database.serializers.ColorSerializer;
import com.uddernetworks.lak.database.serializers.KeyEnumSerializer;
import com.uddernetworks.lak.keys.KeyEnum;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.awt.Color;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
@ContextConfiguration(classes = KeyEnumSerializer.class)
class KeyEnumSerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void keyEnumTester() {
        // Ensures all keys can be serialized/deserialized successfully
        var strings = Arrays.stream(KeyEnum.values()).map(this::writeValueAsString).toArray(String[]::new);
        var actual = Arrays.stream(strings).map(this::readValue).toArray(KeyEnum[]::new);

        assertArrayEquals(KeyEnum.values(), actual);
    }

    private KeyEnum readValue(String value) {
        try {
            return objectMapper.readValue(value, KeyEnum.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred while deserializing KeyEnum");
        }
    }

    private String writeValueAsString(KeyEnum keyEnum) {
        try {
            return objectMapper.writeValueAsString(keyEnum);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("An error occurred while serializing KeyEnum");
        }
    }

}
