package com.uddernetworks.lak.pi.button;

import java.util.function.Consumer;

/**
 * A button that is toggleable down or up.
 */
public class ToggleButton implements Button<ButtonId> {

    private final ButtonId buttonId;
    private final String name;

    private Consumer<Boolean> listener;
    private boolean pressed;

    public ToggleButton(ButtonId buttonId, String name) {
        this.buttonId = buttonId;
        this.name = name;
    }

    @Override
    public ButtonId getId() {
        return buttonId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setListener(Consumer<Boolean> listener) {
        this.listener = listener;
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void setPressed(boolean pressed) {
        if (this.pressed != pressed && listener != null) {
            listener.accept(pressed);
        }
        this.pressed = pressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToggleButton that = (ToggleButton) o;
        return buttonId == that.buttonId;
    }

    @Override
    public int hashCode() {
        return buttonId.hashCode();
    }
}
