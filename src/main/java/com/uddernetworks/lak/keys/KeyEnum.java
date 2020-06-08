package com.uddernetworks.lak.keys;

import java.util.Arrays;
import java.util.Optional;

public enum KeyEnum {
    // TODO: Populate from PDF
    ;

    private int id;

    KeyEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<KeyEnum> fromId(int keyId) {
        return Arrays.stream(values()).filter(key -> key.id == keyId).findFirst();
    }
}
