package com.uddernetworks.lak.rest.key;

import com.uddernetworks.lak.keys.Key;
import com.uddernetworks.lak.keys.KeyEnum;

import java.util.UUID;

public class SmallKey {

    private final KeyEnum key;
    private final UUID soundVariant;
    private final boolean loop;

    public SmallKey(Key key) {
        this.key = key.getKey();
        this.soundVariant = key.getSound().getId();
        this.loop = key.isLoop();
    }

    public KeyEnum getKey() {
        return key;
    }

    public UUID getSoundVariant() {
        return soundVariant;
    }

    public boolean isLoop() {
        return loop;
    }

    @Override
    public String toString() {
        return "SmallKey{" +
                "key=" + key +
                ", soundVariant=" + soundVariant +
                ", loop=" + loop +
                '}';
    }
}
