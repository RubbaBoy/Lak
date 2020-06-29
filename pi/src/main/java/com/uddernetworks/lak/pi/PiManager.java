package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.component.PiComponent;

public interface PiManager {

    /**
     * Registers all {@link PiComponent}s in the implementation.
     */
    void registerComponents();

    /**
     * Starts listening on the {@link PiComponent}'s managers.
     */
    void startListening();

}
