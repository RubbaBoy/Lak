package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.api.light.Light;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.api.light.LightId;
import com.uddernetworks.lak.pi.gpio.PinController;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GPIOLightHandler implements LightHandler<GPIOAbstractedLight> {

    private final PinController pinController;
    private final List<Light<GPIOAbstractedLight>> lights = new ArrayList<>();

    public GPIOLightHandler(@Qualifier("gpioPinController") PinController pinController) {
        this.pinController = pinController;
    }

    @Override
    public Optional<Light<GPIOAbstractedLight>> lightFromId(LightId lightId) {
        return lights.stream().filter(light -> light.getId().getId() == lightId)
                .findFirst();
    }

    @Override
    public LightHandler<GPIOAbstractedLight> registerLight(Light<GPIOAbstractedLight> light) {
        lights.add(light);
        return this;
    }

    @Override
    public List<Light<GPIOAbstractedLight>> getLights() {
        return Collections.unmodifiableList(lights);
    }
}
