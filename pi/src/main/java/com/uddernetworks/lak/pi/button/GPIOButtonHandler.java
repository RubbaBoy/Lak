package com.uddernetworks.lak.pi.button;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("gpioButtonHandler")
public class GPIOButtonHandler implements ButtonHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GPIOButtonHandler.class);

    private boolean listening = false;
    private final List<Button<ButtonId>> buttons = new ArrayList<>();

    @Override
    public void startListening() {
        listening = true;
        // TODO: Interface with GPIO pins to listen to button
        buttons.forEach(button -> {
            LOGGER.debug("Listening on button {}", button.getName());
        });
    }

    @Override
    public ButtonHandler registerButton(Button<ButtonId> button) {
        if (listening) {
            throw new IllegalStateException("Attempted the registry of button while listening has already begun.");
        }

        buttons.add(button);
        return this;
    }

    @Override
    public List<Button<ButtonId>> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
}
