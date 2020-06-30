package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.pi.api.button.Button;
import org.springframework.stereotype.Component;

@Component("caseButtonFactory")
public class CaseButtonFactory {

    public Button<GPIOButtonId> createButton(GPIOButtonId GPIOButtonId) {
        switch (GPIOButtonId) {
            case RED:
            case GREEN:
            case BLUE:
                return new ToggleButton(GPIOButtonId, GPIOButtonId.getName());
            default:
                throw new UnsupportedOperationException("A button with the ID " + GPIOButtonId.name() + " is not supported.");
        }
    }

}
