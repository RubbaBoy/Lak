package com.uddernetworks.lak.pi.light;

import com.uddernetworks.lak.api.ComponentNotFoundException;
import com.uddernetworks.lak.api.light.AbstractedLight;
import com.uddernetworks.lak.api.light.LightId;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;

import java.util.Arrays;

/**
 * IDs for lights, used with {@link LightFactory}.
 */
public enum GPIOAbstractedLight implements AbstractedLight {
    /**
     * The light inside of the {@link GPIOAbstractedButton#RED}.
     */
    BUTTON_RED(LightId.RED_BUTTON, "Red Button", 16),
    /**
     * The light inside of the {@link GPIOAbstractedButton#GREEN}.
     */
    BUTTON_GREEN(LightId.GREEN_BUTTON, "Green Button", 21),
    /**
     * The light inside of the {@link GPIOAbstractedButton#BLUE}.
     */
    BUTTON_BLUE(LightId.BLUE_BUTTON, "Blue Button", 20);

    private final LightId lightId;
    private final int[] gpioPins;
    private final String name;

    GPIOAbstractedLight(LightId lightId, String name, int gpioPin) {
        this(lightId, name, new int[] {gpioPin});
    }

    GPIOAbstractedLight(LightId lightId, String name, int... gpioPins) {
        this.lightId = lightId;
        this.name = name;
        this.gpioPins = gpioPins;
    }

    @Override
    public LightId getId() {
        return lightId;
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

    /**
     * Gets a {@link GPIOAbstractedLight} for the given {@link LightId}.
     *
     * @param lightId The {@link LightId} to get from
     * @return The associated {@link GPIOAbstractedLight}
     */
    public static GPIOAbstractedLight lightFrom(LightId lightId) {
        return Arrays.stream(values())
                .filter(light -> light.lightId == lightId)
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException(lightId));
    }
}
