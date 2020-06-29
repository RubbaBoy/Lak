package com.uddernetworks.lak.pi.light;

import org.springframework.stereotype.Component;

@Component("lightFactory")
public class LightFactory {

    public Light<LightId> createLight(LightId lightId) {
        switch (lightId) {
            case BUTTON_RED:
            case BUTTON_GREEN:
            case BUTTON_BLUE:
                return new SingleLight(lightId, lightId.getName());
            default:
                throw new UnsupportedOperationException("A light with the ID " + lightId.name() + " is not supported.");
        }
    }

}
