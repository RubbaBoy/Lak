package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.api.light.Light;
import com.uddernetworks.lak.pi.gpio.PinController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("lightFactory")
public class LightFactory {

    private final PinController pinController;

    public LightFactory(@Qualifier("gpioPinController") PinController pinController) {
        this.pinController = pinController;
    }

    /**
     * Creates an instance of {@link Light<GPIOAbstractedLight>} from a given {@link GPIOAbstractedLight}.
     *
     * @param gpioLightId The {@link GPIOAbstractedLight}
     * @return The created {@link Light<GPIOAbstractedLight>}
     */
    public Light<GPIOAbstractedLight> createLight(GPIOAbstractedLight gpioLightId) {
        switch (gpioLightId) {
            case BUTTON_RED:
            case BUTTON_GREEN:
            case BUTTON_BLUE:
                return new SingleLight(gpioLightId, (singleLight, high) -> pinController.setPin(singleLight.getId().getGpioPin(), high), gpioLightId.getName());
            default:
                throw new UnsupportedOperationException("A light with the ID " + gpioLightId.name() + " is not supported.");
        }
    }

}
