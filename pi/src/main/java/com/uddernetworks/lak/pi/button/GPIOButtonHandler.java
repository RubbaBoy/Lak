package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.api.button.Button;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.button.ButtonId;
import com.uddernetworks.lak.pi.gpio.PinController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GPIOButtonHandler implements ButtonHandler<GPIOAbstractedButton> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GPIOButtonHandler.class);

    private final PinController pinController;
    private final List<Button<GPIOAbstractedButton>> buttons = new ArrayList<>();
    private boolean listening = false;

    public GPIOButtonHandler(@Qualifier("gpioPinController") PinController pinController) {
        this.pinController = pinController;
    }

    @Override
    public void startListening() {
        listening = true;
        buttons.forEach(button ->
                pinController.addListener(button.getId().getGpioPin(), button::setPressed));
    }

    @Override
    public Optional<Button<GPIOAbstractedButton>> buttonFromId(ButtonId buttonId) {
        return buttons.stream().filter(button -> button.getId().getId() == buttonId)
                .findFirst();
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
