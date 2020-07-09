package com.uddernetworks.lak.pi.button;

import com.uddernetworks.lak.api.ComponentNotFoundException;
import com.uddernetworks.lak.api.button.AbstractedButton;
import com.uddernetworks.lak.api.button.ButtonId;

import java.util.Arrays;

public enum GPIOAbstractedButton implements AbstractedButton {
    RED(ButtonId.RED, "Red", 14), // GPIO_14
    GREEN(ButtonId.GREEN, "Green", 24), // GPIO_24
    BLUE(ButtonId.BLUE, "Blue", 22); // GPIO_22

    private final ButtonId buttonId;
    private final String name;
    private final int[] gpioPins;

    GPIOAbstractedButton(ButtonId buttonId, String name, int gpioPin) {
        this(buttonId, name, new int[] {gpioPin});
    }

    GPIOAbstractedButton(ButtonId buttonId, String name, int... gpioPins) {
        this.buttonId = buttonId;
        this.name = name;
        this.gpioPins = gpioPins;
    }

    @Override
    public ButtonId getId() {
        return buttonId;
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

    /**
     * Gets a {@link GPIOAbstractedButton} for the given {@link ButtonId}.
     *
     * @param buttonId The {@link ButtonId} to get from
     * @return The associated {@link GPIOAbstractedButton}
     */
    public static GPIOAbstractedButton buttonFrom(ButtonId buttonId) {
        return Arrays.stream(values())
                .filter(button -> button.buttonId == buttonId)
                .findFirst()
                .orElseThrow(() -> new ComponentNotFoundException(buttonId));
    }
}
