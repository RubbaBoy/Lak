package com.uddernetworks.lak.hardware;

import com.uddernetworks.lak.api.button.AbstractedButton;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.button.ButtonId;
import com.uddernetworks.lak.api.light.AbstractedLight;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.api.light.LightId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class ButtonInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ButtonInterface.class);

    private final ButtonHandler<AbstractedButton> buttonHandler;
    private final LightHandler<AbstractedLight> lightHandler;

    public ButtonInterface(ButtonHandler<AbstractedButton> buttonHandler,
                           LightHandler<AbstractedLight> lightHandler) {
        this.buttonHandler = buttonHandler;
        this.lightHandler = lightHandler;
    }

    /**
     * Initializes the physical buttons. The following is a description of what each button and its respective light
     * does.
     *
     * <br><br><b>Green Button</b><br>
     * When pressed down, sound is active and the light is on. When up, sound is disabled and the light is off.
     *
     * <br><br><b>Red Button</b><br>
     * TODO
     *
     * <br><br><b>Blue Button</b><br>
     * When down, the light is on and sound is being recorded by the aux in. When up, recording is stopped and the
     * light is off. If the system is not ready for recording, nothing happens.
     */
    public void init() {

        // TODO: Remove initial sequence

        LOGGER.debug("Toggling lights on");
        lightHandler.getLights().forEach(light -> {
            light.setStatus(true);
            sleep(1000);
        });

        LOGGER.debug("Toggling lights off");
        sleep(2000);

        lightHandler.getLights().forEach(light -> {
            light.setStatus(false);
            sleep(1000);
        });

        // End initial sequence

        var buttonLights = Map.of(
                ButtonId.RED, LightId.RED_BUTTON,
                ButtonId.GREEN, LightId.GREEN_BUTTON,
                ButtonId.BLUE, LightId.BLUE_BUTTON
        );

        buttonHandler.getButtons().forEach(button ->
                button.setListener(pressed -> {
                    LOGGER.info("[{}] Pressed: {}", button.getName(), pressed);
                    lightHandler.lightFromId(buttonLights.get(button.getId().getId())).ifPresent(light ->
                            light.setStatus(pressed));
                }));



        buttonHandler.buttonFromId(ButtonId.GREEN).ifPresent(button -> button.setListener(pressed -> {

            lightHandler.lightFromId(LightId.GREEN_BUTTON).ifPresent(light -> light.setStatus(pressed));
        }));

        var redButton = buttonHandler.buttonFromId(ButtonId.RED);
        LOGGER.debug("Button = {}", redButton);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }

}
