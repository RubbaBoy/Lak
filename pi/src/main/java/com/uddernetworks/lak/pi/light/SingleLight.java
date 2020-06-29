package com.uddernetworks.lak.pi.light;

/**
 * A simple single-color LED.
 */
public class SingleLight implements Light<LightId> {

    private final LightId lightId;
    private final String name;
    private boolean status = false;

    public SingleLight(LightId lightId, String name) {
        this.lightId = lightId;
        this.name = name;
    }

    @Override
    public LightId getId() {
        return lightId;
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
