package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.Light;
import org.springframework.stereotype.Component;

@Component("lightFactory")
public class LightFactory {

    /**
     * Creates an instance of {@link Light< GPIOAbstractedLight >} from a given {@link GPIOAbstractedLight}.
     *
     * @param GPIOLightId The {@link GPIOAbstractedLight}
     * @return The created {@link Light< GPIOAbstractedLight >}
     */
    public Light<GPIOAbstractedLight> createLight(GPIOAbstractedLight GPIOLightId) {
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
