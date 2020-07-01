package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.pi.api.button.Button;
import com.uddernetworks.lak.pi.api.button.ButtonHandler;
import com.uddernetworks.lak.pi.api.button.ButtonId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GPIOButtonHandler implements ButtonHandler<GPIOAbstractedButton> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GPIOButtonHandler.class);

    private boolean listening = false;
    private final List<Button<GPIOAbstractedButton>> buttons = new ArrayList<>();

    @Override
    public void startListening() {
        listening = true;
        // TODO: Interface with GPIO pins to listen to button
        buttons.forEach(button -> {
            LOGGER.debug("Listening on button {}", button.getName());
        });
    }

    @Override
    public GPIOAbstractedButton buttonFromId(ButtonId buttonId) {
        return null;
    }

    @Override
    public ButtonHandler<GPIOAbstractedButton> registerButton(Button<GPIOAbstractedButton> button) {
        if (listening) {
            throw new IllegalStateException("Attempted the registry of button while listening has already begun.");
        }

        buttons.add(button);
        return this;
    }

    @Override
    public List<Button<GPIOAbstractedButton>> getButtons() {
        return Collections.unmodifiableList(buttons);
    }
}
