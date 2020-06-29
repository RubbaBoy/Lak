package com.uddernetworks.lak.pi.button;

public enum ButtonId {
    RED(0, "Red"),
    GREEN(1, "Green"),
    BLUE(2, "Blue");

    private final int gpioPin;
    private final String name;

    ButtonId(int gpioPin, String name) {
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
