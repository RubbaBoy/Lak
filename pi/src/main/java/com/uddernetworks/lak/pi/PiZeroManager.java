package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.api.PiManager;
import com.uddernetworks.lak.pi.api.button.ButtonHandler;
import com.uddernetworks.lak.pi.api.light.LightHandler;
import com.uddernetworks.lak.pi.button.GPIOButtonId;
import com.uddernetworks.lak.pi.button.CaseButtonFactory;
import com.uddernetworks.lak.pi.light.LightFactory;
import com.uddernetworks.lak.pi.light.GPIOLightId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("piZeroManager")
public class PiZeroManager implements PiManager {

    private final ButtonHandler<GPIOButtonId> buttonHandler;
    private final LightHandler<GPIOLightId> lightHandler;

    private final CaseButtonFactory caseButtonFactory;
    private final LightFactory lightFactory;

    public PiZeroManager(@Qualifier("gpioButtonHandler") ButtonHandler<GPIOButtonId> buttonHandler,
                         @Qualifier("gpioLightHandler") LightHandler<GPIOLightId> lightHandler,
                         @Qualifier("caseButtonFactory") CaseButtonFactory caseButtonFactory,
                         @Qualifier("lightFactory") LightFactory lightFactory) {
        this.buttonHandler = buttonHandler;
        this.lightHandler = lightHandler;
        this.caseButtonFactory = caseButtonFactory;
        this.lightFactory = lightFactory;
    }

    @Override
    public void registerComponents() {
        buttonHandler
                .registerButton(caseButtonFactory.createButton(GPIOButtonId.RED))
                .registerButton(caseButtonFactory.createButton(GPIOButtonId.GREEN))
                .registerButton(caseButtonFactory.createButton(GPIOButtonId.BLUE));

        lightHandler
                .registerLight(lightFactory.createLight(GPIOLightId.BUTTON_RED))
                .registerLight(lightFactory.createLight(GPIOLightId.BUTTON_GREEN))
                .registerLight(lightFactory.createLight(GPIOLightId.BUTTON_BLUE));
    }

    @Override
    public void startListening() {
        buttonHandler.startListening();
    }
}
