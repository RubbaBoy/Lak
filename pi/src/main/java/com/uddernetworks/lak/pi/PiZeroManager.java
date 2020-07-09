package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.api.PiManager;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.pi.button.CaseButtonFactory;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import com.uddernetworks.lak.pi.gpio.PinController;
import com.uddernetworks.lak.pi.light.GPIOAbstractedLight;
import com.uddernetworks.lak.pi.light.LightFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PiZeroManager implements PiManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PiZeroManager.class);

    private final PinController pinController;
    private final ButtonHandler<GPIOAbstractedButton> buttonHandler;
    private final LightHandler<GPIOAbstractedLight> lightHandler;

    private final CaseButtonFactory caseButtonFactory;
    private final LightFactory lightFactory;

    public PiZeroManager(PinController pinController,
                         ButtonHandler<GPIOAbstractedButton> buttonHandler,
                         LightHandler<GPIOAbstractedLight> lightHandler,
                         CaseButtonFactory caseButtonFactory,
                         LightFactory lightFactory) {
        this.pinController = pinController;
        this.buttonHandler = buttonHandler;
        this.lightHandler = lightHandler;
        this.caseButtonFactory = caseButtonFactory;
        this.lightFactory = lightFactory;
    }

    @Override
    public void init() {
        LOGGER.debug("Registering components...");
        buttonHandler
                .registerButton(caseButtonFactory.createButton(GPIOAbstractedButton.RED))
                .registerButton(caseButtonFactory.createButton(GPIOAbstractedButton.GREEN))
                .registerButton(caseButtonFactory.createButton(GPIOAbstractedButton.BLUE));

        lightHandler
                .registerLight(lightFactory.createLight(GPIOAbstractedLight.BUTTON_RED))
                .registerLight(lightFactory.createLight(GPIOAbstractedLight.BUTTON_GREEN))
                .registerLight(lightFactory.createLight(GPIOAbstractedLight.BUTTON_BLUE));

        LOGGER.debug("Provisioning pins...");
        pinController.provisionPins();
    }

    @Override
    public void startListening() {
        LOGGER.debug("Start listening...");
        buttonHandler.startListening();
    }
}
