package com.uddernetworks.lak.pi.gpio;

import com.uddernetworks.lak.pi.CommandRunner;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import com.uddernetworks.lak.pi.light.GPIOAbstractedLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component("gpioPinController")
public class GPIOPinController implements PinController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GPIOPinController.class);

    private final PinHandler pinHandler;
    private final List<Integer> inputPins = new ArrayList<>();
    private final List<Integer> outputPins = new ArrayList<>();
    private final Map<Integer, PinValue> previousInputs = Collections.synchronizedMap(new HashMap<>());
    private final Map<Integer, Consumer<PinValue>> listeners = Collections.synchronizedMap(new HashMap<>());
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public GPIOPinController(@Qualifier("gpioPinHandler") PinHandler pinHandler) {
        this.pinHandler = pinHandler;
    }

    @Override
    public void provisionPins() {
        Arrays.stream(GPIOAbstractedButton.values()).forEach(button -> {
            var pin = button.getGpioPin();

            LOGGER.debug("Provisioning input pin {}: {}", pin, button.getName());
            pinHandler.exportPin(pin);
            pinHandler.setDirection(pin, PinDirection.IN);
            pinHandler.setActiveLow(pin, PinActiveLow.TRUE);
            pinHandler.setEdge(pin, PinEdge.BOTH);

            // TODO: Move this to an abstracted method
            // sysfs doesn't have pull setting support. This sets the pin as pull up
            CommandRunner.runCommand("raspi-gpio", "set", String.valueOf(pin), "pu");

            inputPins.add(pin);
        });

        Arrays.stream(GPIOAbstractedLight.values()).forEach(light -> {
            var pin = light.getGpioPin();

            LOGGER.debug("Provisioning output pin {}: {}", pin, light.getName());
            pinHandler.exportPin(pin);
            pinHandler.setDirection(pin, PinDirection.OUT);
            pinHandler.setValue(pin, PinValue.LOW);

            outputPins.add(pin);
        });

        LOGGER.debug("Starting listener");
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            for (int pin : listeners.keySet()) {
                var value = pinHandler.readValue(pin);
                if (value != previousInputs.get(pin)) {
                    listeners.get(pin).accept(value);
                    previousInputs.put(pin, value);
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void highPin(int pin) {
        setPin(pin, true);
    }

    @Override
    public void lowPin(int pin) {
        setPin(pin, false);
    }

    @Override
    public void setPin(int pin, boolean high) {
        if (outputPins.contains(pin)) {
            pinHandler.setValue(pin, high ? PinValue.HIGH : PinValue.LOW);
        }
    }

    @Override
    public void addListener(int pin, Consumer<Boolean> listener) {
        if (!inputPins.contains(pin)) {
            LOGGER.warn("Tried to add a listener to pin {} which was not registered", pin);
            return;
        }

        LOGGER.debug("Adding listener for pin {}", pin);
        listeners.put(pin, value -> {
            LOGGER.debug("Change pin {}: {}", pin, value.name());
            listener.accept(value.isHigh());
        });
    }
}
