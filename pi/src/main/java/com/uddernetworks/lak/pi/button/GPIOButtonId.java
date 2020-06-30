package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.pi.api.button.ButtonId;

public enum GPIOButtonId implements ButtonId {
    RED("Red", 0),
    GREEN("Green", 1),
    BLUE("Blue", 2);

    private final String name;
    private final int[] gpioPins;

    GPIOButtonId(String name, int gpioPin) {
        this(name, new int[] {gpioPin});
    }

    GPIOButtonId(String name, int... gpioPins) {
        this.name = name;
        this.gpioPins = gpioPins;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the Raspberry Pi GPIO pin this button uses. If multiple pins were set in the enum's constructor, the first
     * one is gotten.
     *
     * @return The first GPIO pin
     */
    public int getGpioPin() {
        return gpioPins[0];
    }

    /**
     * Gets all the Raspberry Pi GPIO pins this button uses.
     *
     * @return All set GPIO pins for the light
     */
    public int[] getGpioPins() {
        return gpioPins;
    }
}
