package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.api.light.AbstractedLight;
import com.uddernetworks.lak.api.light.Light;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.api.light.LightId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DummyLightHandler implements LightHandler<AbstractedLight> {

    @Override
    public Optional<Light<AbstractedLight>> lightFromId(LightId lightId) {
        return Optional.empty();
    }

    @Override
    public LightHandler<AbstractedLight> registerLight(Light<AbstractedLight> light) {
        return this;
    }

    @Override
    public List<Light<AbstractedLight>> getLights() {
        return Collections.emptyList();
    }
}
