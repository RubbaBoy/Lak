package com.uddernetworks.lak.pi.api.button;

/**
 * Additional information bound to each {@link ButtonId} for implementation-specific information regarding it.
 * This should be an enum, and only be used as an identifier of static information.
 */
public interface AbstractedButton {

    /**
     * Gets the {@link ButtonId}.
     *
     * @return The {@link ButtonId}
     */
    ButtonId getId();

    /**
     * Gets the display name for the button.
     *
     * @return The button's name
     */
    String getName();
}
