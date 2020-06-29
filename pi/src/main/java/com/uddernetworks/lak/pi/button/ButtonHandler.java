package com.uddernetworks.lak.pi.button;

import java.util.List;

public interface ButtonHandler {

    /**
     * Starts handling buttons triggers.
     */
    void startListening();

    /**
     * Registers a button to handle. This must be invoked before {@link #startListening()} has been invoked.
     *
     * @param button The button to register
     * @return The current {@link ButtonHandler} for chaining
     */
    ButtonHandler registerButton(Button<ButtonId> button);

    /**
     * Gets an unmodifiable list of registered buttons.
     *
     * @return An unmodifiable list of registered buttons
     */
    List<Button<ButtonId>> getButtons();

}
