package com.uddernetworks.lak.keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Outputs a given {@link KeyEnum} to /dev/hidg0 from instructions via
 * https://randomnerdtutorials.com/raspberry-pi-zero-usb-keyboard-hid/
 */
@Component("usbKeyboardOutput")
public class USBKeyboardOutput implements KeyboardOutput {

    private static final Logger LOGGER = LoggerFactory.getLogger(USBKeyboardOutput.class);

    @Override
    public void outputKey(KeyEnum keyEnum) {
        LOGGER.warn("TODO: Output {}", keyEnum);
    }

}
