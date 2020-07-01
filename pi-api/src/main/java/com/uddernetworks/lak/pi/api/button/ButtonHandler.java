package com.uddernetworks.lak.pi.api.button;

import java.util.List;
import java.util.Optional;

public interface ButtonHandler<T extends AbstractedButton> {

    /**
     * Starts handling buttons triggers.
     */
    void startListening();

    /**
     * Gets an {@link Button} from its given {@link ButtonId}. The implementation may not contain the specific
     * {@link Button}, in which case an empty optional is given.
     *
     * @param buttonId The {@link ButtonId} to get from
     * @return The associated {@link Button}
     */
    Optional<Button<T>> buttonFromId(ButtonId buttonId);

    /**
     * Registers a button to handle. This must be invoked before {@link #startListening()} has been invoked.
     *
     * @param button The button to register
     * @return The current {@link ButtonHandler} for chaining
     */
    ButtonHandler<T> registerButton(Button<T> button);

    /**
     * Gets an unmodifiable list of registered buttons.
     *
     * @return An unmodifiable list of registered buttons
     */
    List<Button<T>> getButtons();

}
