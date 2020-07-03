package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.api.button.AbstractedButton;
import com.uddernetworks.lak.api.button.Button;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.button.ButtonId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DummyButtonHandler implements ButtonHandler<AbstractedButton> {

    @Override
    public void startListening() {
    }

    @Override
    public Optional<Button<AbstractedButton>> buttonFromId(ButtonId buttonId) {
        return Optional.empty();
    }

    @Override
    public ButtonHandler<AbstractedButton> registerButton(Button<AbstractedButton> button) {
        return this;
    }

    @Override
    public List<Button<AbstractedButton>> getButtons() {
        return Collections.emptyList();
    }
}
