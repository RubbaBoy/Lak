package com.uddernetworks.lak.keys;

import java.util.Arrays;
import java.util.Optional;

public enum KeyAction {
    RELEASED(0),
    PRESSED(1),
    HELD(2);

    private final int value;

    KeyAction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Optional<KeyAction> fromValue(int value) {
        return Arrays.stream(values()).filter(action -> action.value == value).findFirst();
    }
}
