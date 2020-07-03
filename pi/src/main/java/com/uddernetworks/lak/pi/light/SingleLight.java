package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.api.light.Light;

import java.util.function.BiConsumer;

/**
 * A simple single-color LED.
 */
public class SingleLight implements Light<GPIOAbstractedLight> {

    private final GPIOAbstractedLight gpioLightId;
    private final BiConsumer<SingleLight, Boolean> listener;
    private final String name;
    private boolean status = false;

    public SingleLight(GPIOAbstractedLight gpioLightId, BiConsumer<SingleLight, Boolean> listener, String name) {
        this.gpioLightId = gpioLightId;
        this.listener = listener;
        this.name = name;
    }

    @Override
    public GPIOAbstractedLight getId() {
        return gpioLightId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setStatus(boolean on) {
        this.status = on;
        if (listener != null) {
            listener.accept(this, on);
        }
    }

    @Override
    public boolean getStatus() {
        return status;
    }
}
