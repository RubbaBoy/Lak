package com.uddernetworks.lak.sounds.modulation;

import java.util.Arrays;
import java.util.Optional;

public enum ModulationId {
    VOLUME((byte) 0),
    PITCH((byte) 1);

    private final byte id;

    ModulationId(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public static Optional<ModulationId> getFromId(byte id) {
        return Arrays.stream(values()).filter(modulationId -> modulationId.id == id).findFirst();
    }
}
