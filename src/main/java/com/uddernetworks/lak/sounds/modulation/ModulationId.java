package com.uddernetworks.lak.sounds.modulation;

public enum ModulationId {
    VOLUME(0),
    PITCH(1);

    private int id;

    ModulationId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
