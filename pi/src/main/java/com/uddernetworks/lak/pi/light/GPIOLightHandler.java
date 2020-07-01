package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.Light;
import com.uddernetworks.lak.pi.api.light.LightHandler;
import com.uddernetworks.lak.pi.api.light.LightId;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GPIOLightHandler implements LightHandler<GPIOAbstractedLight> {

    private final List<Light<GPIOAbstractedLight>> lights = new ArrayList<>();

    @Override
    public GPIOAbstractedLight lightFromId(LightId lightId) {
        return GPIOAbstractedLight.lightFrom(lightId);
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

    @Override
    public Optional<Light<GPIOAbstractedLight>> getLight(GPIOAbstractedLight id) {
        return lights.stream().filter(light -> light.getId() == id).findFirst();
    }
}
