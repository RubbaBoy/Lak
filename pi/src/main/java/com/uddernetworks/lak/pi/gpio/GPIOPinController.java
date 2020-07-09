package com.uddernetworks.lak.pi.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import com.uddernetworks.lak.pi.light.GPIOAbstractedLight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component("gpioPinController")
public class GPIOPinController implements PinController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GPIOPinController.class);

    private final GpioController gpio;
    private final Map<Integer, GpioPinDigitalInput> inputPins = new HashMap<>();
    private final Map<Integer, GpioPinDigitalOutput> outputPins = new HashMap<>();

    public GPIOPinController(@Autowired GpioController gpio) {
        this.gpio = gpio;
    }

    private Pin fromId(int id) {
        return RaspiPin.getPinByAddress(id);
    }

    @Override
    public void provisionPins() {
        Arrays.stream(GPIOAbstractedButton.values()).forEach(button -> {
            var pin = button.getGpioPin();
            LOGGER.debug("Provisioning pin {} - {}", fromId(pin).getName(), button.getName());
            inputPins.put(pin, gpio.provisionDigitalInputPin(fromId(pin), button.getName()));
        });

        Arrays.stream(GPIOAbstractedLight.values()).forEach(light -> {
            var pin = light.getGpioPin();
            var outputPin = gpio.provisionDigitalOutputPin(fromId(pin), light.getName(), PinState.LOW);
            outputPin.setShutdownOptions(true, PinState.LOW);
            outputPins.put(pin, outputPin);
        });
    }

    @Override
    public void highPin(int pin) {
        outputPins.get(pin).high();
    }

    @Override
    public void lowPin(int pin) {
        outputPins.get(pin).low();
    }

    @Override
    public void setPin(int pin, boolean high) {
        outputPins.get(pin).setState(high);
    }

    @Override
    public void addListener(int pin, Consumer<Boolean> listener) {
        LOGGER.debug("Adding listener for pin {}", pin);
        inputPins.get(pin).addListener((GpioPinListenerDigital) event -> {
            LOGGER.debug("Change pin {} high: {}", pin, event.getState().isHigh());
            listener.accept(event.getState().isHigh());
        });
    }
}
