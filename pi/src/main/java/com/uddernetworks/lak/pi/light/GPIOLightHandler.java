package com.uddernetworks.lak.pi.light;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("gpioLightHandler")
public class GPIOLightHandler implements LightHandler {

    private final List<Light<LightId>> lights = new ArrayList<>();

    @Override
    public LightHandler registerLight(Light<LightId> light) {
        lights.add(light);
        return this;
    }

    @Override
    public List<Light<LightId>> getLights() {
        return Collections.unmodifiableList(lights);
    }

    @Override
    public Optional<Light<LightId>> getLight(LightId id) {
        return lights.stream().filter(light -> light.getId() == id).findFirst();
    }
}
