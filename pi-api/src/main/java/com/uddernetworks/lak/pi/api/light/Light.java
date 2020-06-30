package com.uddernetworks.lak.pi.api.light;

import com.uddernetworks.lak.pi.api.component.PiComponent;

public interface Light<T extends LightId> extends PiComponent<T> {

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
