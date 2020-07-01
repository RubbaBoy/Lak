package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.pi.api.button.AbstractedButton;
import com.uddernetworks.lak.pi.api.button.ButtonId;

import java.util.Arrays;

public enum DummyButton implements AbstractedButton {
    RED(ButtonId.RED),
    GREEN(ButtonId.GREEN),
    BLUE(ButtonId.BLUE),
    ;

    private final ButtonId buttonId;

    DummyButton(ButtonId buttonId) {
        this.buttonId = buttonId;
    }

    @Override
    public ButtonId getId() {
        return buttonId;
    }

    @Override
    public String getName() {
        return "DUMMY_" + buttonId.name();
    }

    /**
     * Gets a {@link DummyButton} for the given {@link ButtonId}.
     *
     * @param buttonId The {@link ButtonId} to get from
     * @return The associated {@link DummyButton}
     */
    public static DummyButton buttonFrom(ButtonId buttonId) {
        return Arrays.stream(values())
                .filter(button -> button.buttonId == buttonId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No DummyButton found for " + buttonId.name()));
    }
}
