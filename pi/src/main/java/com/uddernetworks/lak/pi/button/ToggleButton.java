package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.api.button.Button;

import java.util.function.Consumer;

/**
 * A button that is toggleable down or up.
 */
public class ToggleButton implements Button<GPIOAbstractedButton> {

    private final GPIOAbstractedButton GPIOButtonId;
    private final String name;

    private Consumer<Boolean> listener;
    private boolean pressed;

    public ToggleButton(GPIOAbstractedButton GPIOButtonId, String name) {
        this.GPIOButtonId = GPIOButtonId;
        this.name = name;
    }

    @Override
    public GPIOAbstractedButton getId() {
        return GPIOButtonId;
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
        return GPIOButtonId == that.GPIOButtonId;
    }

    @Override
    public int hashCode() {
        return GPIOButtonId.hashCode();
    }
}
