package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.Light;
import com.uddernetworks.lak.pi.api.light.LightHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component("gpioLightHandler")
public class GPIOLightHandler implements LightHandler<GPIOLightId> {

    private final List<Light<GPIOLightId>> lights = new ArrayList<>();

    @Override
    public LightHandler<GPIOLightId> registerLight(Light<GPIOLightId> light) {
        lights.add(light);
        return this;
    }

    @Override
    public List<Light<GPIOLightId>> getLights() {
        return Collections.unmodifiableList(lights);
    }

    @Override
    public Optional<Light<GPIOLightId>> getLight(GPIOLightId id) {
        return lights.stream().filter(light -> light.getId() == id).findFirst();
    }
}
