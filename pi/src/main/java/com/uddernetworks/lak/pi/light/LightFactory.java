package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.Light;
import org.springframework.stereotype.Component;

@Component("lightFactory")
public class LightFactory {

    /**
     * Creates an instance of {@link Light< GPIOLightId >} from a given {@link GPIOLightId}.
     *
     * @param GPIOLightId The {@link GPIOLightId}
     * @return The created {@link Light< GPIOLightId >}
     */
    public Light<GPIOLightId> createLight(GPIOLightId GPIOLightId) {
        switch (GPIOLightId) {
            case BUTTON_RED:
            case BUTTON_GREEN:
            case BUTTON_BLUE:
                return new SingleLight(GPIOLightId, GPIOLightId.getName());
            default:
                throw new UnsupportedOperationException("A light with the ID " + GPIOLightId.name() + " is not supported.");
        }
    }

}
