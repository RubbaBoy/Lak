package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.pi.api.light.LightId;
import com.uddernetworks.lak.pi.button.GPIOButtonId;

/**
 * IDs for lights, used with {@link LightFactory}.
 */
public enum GPIOLightId implements LightId {
    /**
     * The light inside of the {@link GPIOButtonId#RED}.
     */
    BUTTON_RED("Red Button", 0),
    /**
     * The light inside of the {@link GPIOButtonId#GREEN}.
     */
    BUTTON_GREEN("Green Button", 1),
    /**
     * The light inside of the {@link GPIOButtonId#BLUE}.
     */
    BUTTON_BLUE("Blue Button", 2);

    private final int[] gpioPins;
    private final String name;

    GPIOLightId(String name, int gpioPin) {
        this(name, new int[] {gpioPin});
    }

    GPIOLightId(String name, int... gpioPins) {
        this.name = name;
        this.gpioPins = gpioPins;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the Raspberry Pi GPIO pin this light uses. If multiple pins were set in the enum's constructor, the first
     * one is gotten.
     *
     * @return The first GPIO pin
     */
    public int getGpioPin() {
        return gpioPins[0];
    }

    /**
     * Gets all the Raspberry Pi GPIO pins this light uses.
     *
     * @return All set GPIO pins for the light
     */
    public int[] getGpioPins() {
        return gpioPins;
    }
}
