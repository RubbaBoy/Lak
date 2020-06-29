package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.button.ButtonId;

public enum LightId {
    /**
     * The light inside of the {@link ButtonId#RED}.
     */
    BUTTON_RED(0, "Red Button"),
    /**
     * The light inside of the {@link ButtonId#GREEN}.
     */
    BUTTON_GREEN(1, "Green Button"),
    /**
     * The light inside of the {@link ButtonId#BLUE}.
     */
    BUTTON_BLUE(2, "Blue Button");

    private final int gpioPin;
    private final String name;

    LightId(int gpioPin, String name) {
        this.gpioPin = gpioPin;
        this.name = name;
    }

    public int getGpioPin() {
        return gpioPin;
    }

    public String getName() {
        return name;
    }
}
