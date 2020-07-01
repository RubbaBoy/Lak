package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.api.PiManager;
import com.uddernetworks.lak.pi.api.button.ButtonHandler;
import com.uddernetworks.lak.pi.api.light.LightHandler;
import com.uddernetworks.lak.pi.button.CaseButtonFactory;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import com.uddernetworks.lak.pi.light.GPIOAbstractedLight;
import com.uddernetworks.lak.pi.light.LightFactory;

public class PiZeroManager implements PiManager {

    private final ButtonHandler<GPIOAbstractedButton> buttonHandler;
    private final LightHandler<GPIOAbstractedLight> lightHandler;

    private final CaseButtonFactory caseButtonFactory;
    private final LightFactory lightFactory;

    public PiZeroManager(ButtonHandler<GPIOAbstractedButton> buttonHandler,
                         LightHandler<GPIOAbstractedLight> lightHandler,
                         CaseButtonFactory caseButtonFactory,
                         LightFactory lightFactory) {
        this.buttonHandler = buttonHandler;
        this.lightHandler = lightHandler;
        this.caseButtonFactory = caseButtonFactory;
        this.lightFactory = lightFactory;
    }

    @Override
    public void registerComponents() {
        buttonHandler
                .registerButton(caseButtonFactory.createButton(GPIOAbstractedButton.RED))
                .registerButton(caseButtonFactory.createButton(GPIOAbstractedButton.GREEN))
                .registerButton(caseButtonFactory.createButton(GPIOAbstractedButton.BLUE));

        lightHandler
                .registerLight(lightFactory.createLight(GPIOAbstractedLight.BUTTON_RED))
                .registerLight(lightFactory.createLight(GPIOAbstractedLight.BUTTON_GREEN))
                .registerLight(lightFactory.createLight(GPIOAbstractedLight.BUTTON_BLUE));
    }

    @Override
    public void startListening() {
        buttonHandler.startListening();
    }
}
