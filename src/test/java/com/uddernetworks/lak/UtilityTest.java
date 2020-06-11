package com.uddernetworks.lak;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtilityTest {

    @Test
    void clamp() {
        assertEquals(Utility.clamp(-10, 0, 5), 0);
        assertEquals(Utility.clamp(10, 0, 5), 5);
        assertEquals(Utility.clamp(2, 0, 5), 2);
    }

    @Test
    void hexFromColor() {
        var color = Utility.hexFromColor(new Color(187, 204, 221, 170));

        assertEquals("AABBCCDD", color);
    }

    @Test
    void colorFromHex() {
        var color = Utility.colorFromHex("AABBCCDD");

        assertNotNull(color);
        assertEquals(170, color.getAlpha());
        assertEquals(187, color.getRed());
        assertEquals(204, color.getGreen());
        assertEquals(221, color.getBlue());
    }

    @Test
    void getBytesFromUUID() {
        var uuid = UUID.fromString("00a64946-98a6-4217-9267-35b49a7b6e2d");
        var bytes = new byte[] {0, -90, 73, 70, -104, -90, 66, 23, -110, 103, 53, -76, -102, 123, 110, 45};

        assertArrayEquals(Utility.getBytesFromUUID(uuid), bytes);
    }

    @Test
    void getUUIDFromBytes() {
        var uuid = UUID.fromString("00a64946-98a6-4217-9267-35b49a7b6e2d");
        var bytes = new byte[] {0, -90, 73, 70, -104, -90, 66, 23, -110, 103, 53, -76, -102, 123, 110, 45};

        assertEquals(Utility.getUUIDFromBytes(bytes), uuid);
    }

    @Test
    void copyBufferArray() {
        var initial = new byte[] {1, 2, 3};
        var copied = Utility.copyBuffer(initial, 3).array();

        assertArrayEquals(new byte[] {1, 2, 3, 0, 0, 0}, copied);
    }

    @Test
    void copyBuffer() {
        var initial = ByteBuffer.wrap(new byte[] {1, 2, 3});
        var copied = Utility.copyBuffer(initial, 3).array();

        assertArrayEquals(new byte[] {1, 2, 3, 0, 0, 0}, copied);
    }

    @Test
    void readResource() throws IOException {
        assertArrayEquals("some\r\ndata\r\n".getBytes(), Utility.readResource("data/text.txt"));
    }

    @Test
    void readResourceString() throws IOException {
        assertEquals("some\r\ndata\r\n", Utility.readResourceString("data/text.txt"));
    }
}