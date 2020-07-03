package com.uddernetworks.lak.api.light;

import com.uddernetworks.lak.api.component.PiComponent;

import java.util.function.Consumer;

public interface Light<T extends AbstractedLight> extends PiComponent<T> {

    /**
     * Sets the status of the light, if it should be on. See implementation for possibly more advanced toggling features
     * if any lights support them.
     *
     * @param on If the light should be on
     */
    void setStatus(boolean on);

    /**
     * Gets if the light is on at all. See implementation for possibly more advanced toggling features
     * if any lights support them.
     *
     * @return If the light is on
     */
    boolean getStatus();
}
