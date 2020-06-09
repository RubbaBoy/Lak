package com.uddernetworks.lak;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void clamp() {
        assertEquals(Utility.clamp(-10, 0, 5), 0);
        assertEquals(Utility.clamp(10, 0, 5), 5);
        assertEquals(Utility.clamp(2, 0, 5), 2);
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
        assertArrayEquals(Utility.copyBuffer(initial, 3).array(), new byte[] {1, 2, 3, 0, 0, 0});
    }

    @Test
    void copyBuffer() {
        var initial = ByteBuffer.wrap(new byte[] {1, 2, 3});
        assertArrayEquals(Utility.copyBuffer(initial, 3).array(), new byte[] {1, 2, 3, 0, 0, 0});
    }

    @Test
    void readResource() throws IOException {
        assertArrayEquals(Utility.readResource("data/text.txt"), "some\r\ndata\r\n".getBytes());
    }

    @Test
    void readResourceString() throws IOException {
        assertEquals(Utility.readResourceString("data/text.txt"), "some\r\ndata\r\n");
    }
}