package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.Light;

/**
 * A simple single-color LED.
 */
public class SingleLight implements Light<GPIOLightId> {

    private final GPIOLightId GPIOLightId;
    private final String name;
    private boolean status = false;

    public SingleLight(GPIOLightId GPIOLightId, String name) {
        this.GPIOLightId = GPIOLightId;
        this.name = name;
    }

    @Override
    public GPIOLightId getId() {
        return GPIOLightId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setStatus(boolean on) {
        // TODO: Interface with GPIO to set light on
        this.status = on;
    }

    @Override
    public boolean getStatus() {
        return status;
    }
}
