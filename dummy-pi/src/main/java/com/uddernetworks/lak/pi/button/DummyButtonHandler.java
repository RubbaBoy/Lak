package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.pi.api.button.AbstractedButton;
import com.uddernetworks.lak.pi.api.button.Button;
import com.uddernetworks.lak.pi.api.button.ButtonHandler;
import com.uddernetworks.lak.pi.api.button.ButtonId;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

public class DummyButtonHandler implements ButtonHandler<AbstractedButton> {

    @Override
    public void startListening() {
    }

    @Override
    public AbstractedButton buttonFromId(ButtonId buttonId) {
        return DummyButton.buttonFrom(buttonId);
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
