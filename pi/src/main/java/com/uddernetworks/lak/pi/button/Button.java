package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.pi.component.PiComponent;

import java.util.function.Consumer;

/**
 * An object usually representing one physical button on the keyboard case.
 *
 * @param <T> The button identifying type
 */
public interface Button<T> extends PiComponent<T> {

    /**
     * Sets the listener to a consumer that is accepted when the current button is pressed, accepting the new boolean
     * result of {@link #isPressed()}.
     *
     * @param listener The consumer called when the button changes states, overwriting the old listener
     */
    void setListener(Consumer<Boolean> listener);

    /**
     * Returns if the button is actively pressed down.
     *
     * @return If the button is pressed down
     */
    boolean isPressed();

    /**
     * Sets the pressed status of the button, invoking the listener if set and the press status changes.
     *
     * @param pressed The pressed status
     */
    void setPressed(boolean pressed);
}
