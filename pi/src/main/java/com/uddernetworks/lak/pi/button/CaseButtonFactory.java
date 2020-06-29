package com.uddernetworks.lak.pi.button;

import org.springframework.stereotype.Component;

@Component("caseButtonFactory")
public class CaseButtonFactory {

    public Button<ButtonId> createButton(ButtonId buttonId) {
        switch (buttonId) {
            case RED:
            case GREEN:
            case BLUE:
                return new ToggleButton(buttonId, buttonId.getName());
            default:
                throw new UnsupportedOperationException("A button with the ID " + buttonId.name() + " is not supported.");
        }
    }

}
