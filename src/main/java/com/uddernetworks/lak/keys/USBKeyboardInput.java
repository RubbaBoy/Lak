package com.uddernetworks.lak.keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A linux-only key detector straight from /dev/input/event0
 * This must be ran with elevated permissions.
 */
@Component("usbKeyboardInput")
public class USBKeyboardInput implements KeyboardInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(USBKeyboardInput.class);

    private final KeyboardInterceptor keyboardInterceptor;

    private final ReentrantLock lock = new ReentrantLock();

    public USBKeyboardInput(@Qualifier("soundKeyboardInterceptor") KeyboardInterceptor keyboardInterceptor) {
        this.keyboardInterceptor = keyboardInterceptor;
    }

    @Override
    public void init() {
        lock.lock();
        CompletableFuture.runAsync(() -> {
            try {
                var bruh = new FileInputStream(new File("/dev/input/event0"));
                var read = new byte[24];
                while (true) {
                    try {
                        lock.tryLock(9999, TimeUnit.DAYS);

                        var b = bruh.read(read);
                        if (b == 24) {
                            var buffer = ByteBuffer.wrap(read);
                            var sec = getBigInt(buffer, 8).longValue(); // Seconds
                            var usec = getBigInt(buffer, 8).longValue(); // Microseconds
                            var type = getBigInt(buffer, 2).shortValue(); // Type is for example EV_REL for relative moment, EV_KEY for a keypress or release. More types are defined in include/uapi/linux/input-event-codes.h.
                            var code = getBigInt(buffer, 2).intValue(); // Code is event code, for example REL_X or KEY_BACKSPACE, again a complete list is in include/uapi/linux/input-event-codes.h.
                            var value = getBigInt(buffer, 4).intValue(); // Value is the value the event carries. Either a relative change for EV_REL, absolute new value for EV_ABS (joysticks ...), or 0 for EV_KEY for release, 1 for keypress and 2 for autorepeat.

                            if (type != 1) {
                                continue;
                            }

                            var actionOptional = KeyAction.fromValue(value);

                            if (actionOptional.isEmpty()) {
                                continue;
                            }

                            var keyOptional = KeyEnum.fromLinuxCode(code);
                            if (keyOptional.isEmpty()) {
                                continue;
                            }

                            var key = keyOptional.get();
                            var action = actionOptional.get();

                            keyboardInterceptor.receiveKey(key);

                            System.out.println(sec + " Key: " + key + " " + action.name());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (IOException e) {
                LOGGER.error("An error occurred while reading the file");
            }
        });
    }

    @Override
    public void startListening() {
        lock.unlock();
    }

    @Override
    public void stopListening() {
        lock.lock();
    }

    private static BigInteger getBigInt(ByteBuffer buffer, int length) {
        var arr = new byte[length];
        var arr2 = new byte[length];
        buffer.get(arr);
        var i2 = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            arr2[i2++] = arr[i];
        }
        return new BigInteger(arr2);
    }

    enum KeyAction {
        RELEASED(0),
        PRESSED(1),
        HELD(2);

        private final int value;

        KeyAction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Optional<KeyAction> fromValue(int value) {
            return Arrays.stream(values()).filter(action -> action.value == value).findFirst();
        }
    }
}
