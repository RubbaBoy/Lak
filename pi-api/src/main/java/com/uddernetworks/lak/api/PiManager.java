package com.uddernetworks.lak.api;

import com.uddernetworks.lak.api.component.PiComponent;

public interface PiManager {

    /**
     * Registers all {@link PiComponent}s in the implementation.
     */
    void init();

    /**
     * Starts listening on the {@link PiComponent}'s managers.
     */
    void startListening();

}
