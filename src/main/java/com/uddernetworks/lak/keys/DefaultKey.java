package com.uddernetworks.lak.keys;

import com.uddernetworks.lak.sounds.SoundVariant;

import java.util.Objects;

/**
 * Stores in the keys table
 */
public class DefaultKey implements Key {

    private final KeyEnum key;
    private SoundVariant soundVariant;
    private boolean loop;

    public DefaultKey(KeyEnum key, SoundVariant soundVariant, boolean loop) {
        this.key = key;
        this.soundVariant = soundVariant;
        this.loop = loop;
    }

    @Override
    public KeyEnum getKey() {
        return key;
    }

    @Override
    public SoundVariant getSound() {
        return soundVariant;
    }

    @Override
    public void setSound(SoundVariant soundVariant) {
        this.soundVariant = soundVariant;
    }

    @Override
    public boolean isLoop() {
        return loop;
    }

    @Override
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultKey that = (DefaultKey) o;
        return loop == that.loop &&
                key == that.key &&
                Objects.equals(soundVariant, that.soundVariant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, soundVariant, loop);
    }
}
