package com.uddernetworks.lak;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utility {

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static byte[] getBytesFromUUID(UUID uuid) {
        var bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID getUUIDFromBytes(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    /**
     * Copies a {@link ByteBuffer} with an additional set of bytes at the end to be used. Works the same as using
     * {@link #copyBuffer(byte[], int)} with {@link ByteBuffer#array()}.
     *
     * @param initial       The initial {@link ByteBuffer}
     * @param trailingExtra The extra bytes to be used at the end
     * @return The new {@link ByteBuffer}
     */
    public static ByteBuffer copyBuffer(ByteBuffer initial, int trailingExtra) {
        return copyBuffer(initial.array(), trailingExtra);
    }

    /**
     * Copies a byte array with an additional set of bytes at the end to be used, resulting in a {@link ByteBuffer}.
     *
     * @param initial       The initial byte array
     * @param trailingExtra The extra bytes to be used at the end
     * @return The resultant {@link ByteBuffer}
     */
    public static ByteBuffer copyBuffer(byte[] initial, int trailingExtra) {
        var resulting = ByteBuffer.allocate(initial.length + trailingExtra);
        resulting.put(initial);
        return resulting;
    }

    /**
     * Reads a resource from the internal resources directory. Use / for path separator.
     *
     * @param path The relative path to the file
     * @return The bytes of the file content
     * @throws IOException If an error occurs
     */
    public static byte[] readResource(String path) throws IOException {
        var resource = Objects.requireNonNull(Utility.class.getClassLoader().getResource(path));
        return resource.openStream().readAllBytes();
    }

    /**
     * Reads a resource from the internal resources directory. Use / for path separator.
     *
     * @param path The relative path to the file
     * @return The file content as a String
     * @throws IOException If an error occurs
     */
    public static String readResourceString(String path) throws IOException {
        return new String(readResource(path));
    }
}
