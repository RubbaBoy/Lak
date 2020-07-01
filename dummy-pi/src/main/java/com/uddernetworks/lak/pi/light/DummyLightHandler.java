package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.AbstractedLight;
import com.uddernetworks.lak.pi.api.light.Light;
import com.uddernetworks.lak.pi.api.light.LightHandler;
import com.uddernetworks.lak.pi.api.light.LightId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DummyLightHandler implements LightHandler<AbstractedLight> {

    @Override
    public AbstractedLight lightFromId(LightId lightId) {
        return DummyLight.lightFrom(lightId);
    }

    @Override
    public LightHandler<AbstractedLight> registerLight(Light<AbstractedLight> light) {
        return this;
    }

    @Override
    public List<Light<AbstractedLight>> getLights() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Light<AbstractedLight>> getLight(AbstractedLight id) {
        return Optional.empty();
    }
}
