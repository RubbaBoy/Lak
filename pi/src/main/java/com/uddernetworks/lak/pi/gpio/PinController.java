package com.uddernetworks.lak.pi.gpio;

import java.util.function.Consumer;

/**
 * Controls GPIO in a more abstracted manner, so it's easier to drop in similar replacements.
 */
public interface PinController {

    /**
     * Provisions pins to required output/inputs. Will do nothing if invoked multiple times.
     */
    void provisionPins();

    /**
     * Turns the given pin HIGH.
     *
     * @param pin The pin
     */
    void highPin(int pin);

    /**
     * Turns the given pin LOW.
     *
     * @param pin The pin
     */
    void lowPin(int pin);

    /**
     * Sets the pin to high (true) or low (false).
     *
     * @param pin The pin
     * @param high I the pin should be high
     */
    void setPin(int pin, boolean high);

    /**
     * Adds a listener to the input pin given. The listener is invoked when the pin state changes, the boolean value
     * being if the pin is high.
     *
     * @param pin The pin
     * @param listener The listener, invoked upon state change
     */
    void addListener(int pin, Consumer<Boolean> listener);

}
