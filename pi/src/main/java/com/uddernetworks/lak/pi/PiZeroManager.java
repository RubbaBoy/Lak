package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.button.ButtonHandler;
import com.uddernetworks.lak.pi.button.ButtonId;
import com.uddernetworks.lak.pi.button.CaseButtonFactory;
import com.uddernetworks.lak.pi.light.LightFactory;
import com.uddernetworks.lak.pi.light.LightHandler;
import com.uddernetworks.lak.pi.light.LightId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("piZeroManager")
public class PiZeroManager implements PiManager {

    private final ButtonHandler buttonHandler;
    private final LightHandler lightHandler;

    private final CaseButtonFactory caseButtonFactory;
    private final LightFactory lightFactory;

    public PiZeroManager(@Qualifier("gpioButtonHandler") ButtonHandler buttonHandler,
                         @Qualifier("gpioLightHandler") LightHandler lightHandler,
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
                .registerButton(caseButtonFactory.createButton(ButtonId.RED))
                .registerButton(caseButtonFactory.createButton(ButtonId.GREEN))
                .registerButton(caseButtonFactory.createButton(ButtonId.BLUE));

        lightHandler
                .registerLight(lightFactory.createLight(LightId.BUTTON_RED))
                .registerLight(lightFactory.createLight(LightId.BUTTON_GREEN))
                .registerLight(lightFactory.createLight(LightId.BUTTON_BLUE));
    }

    @Override
    public void startListening() {
        buttonHandler.startListening();
    }
}
