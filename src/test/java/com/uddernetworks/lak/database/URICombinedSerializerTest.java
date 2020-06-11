package com.uddernetworks.lak.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class URICombinedSerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void uriSerializer() throws JsonProcessingException {
        var uri = objectMapper.readValue("\"something/here\"", URI.class);
        assertEquals(URI.create("something/here"), uri);
    }

    @Test
    void uriDeserializer() throws JsonProcessingException {
        var path = objectMapper.writeValueAsString(URI.create("something/here"));
        assertEquals("\"something/here\"", path);
    }

}
