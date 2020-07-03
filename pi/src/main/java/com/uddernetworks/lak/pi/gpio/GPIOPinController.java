package com.uddernetworks.lak.pi.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import com.uddernetworks.lak.pi.light.GPIOAbstractedLight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component("gpioPinController")
public class GPIOPinController implements PinController {

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
            inputPins.put(pin, gpio.provisionDigitalInputPin(fromId(pin), button.getName()));
        });

        Arrays.stream(GPIOAbstractedLight.values()).forEach(light -> {
            var pin = light.getGpioPin();
            outputPins.put(pin, gpio.provisionDigitalOutputPin(fromId(pin), light.getName()));
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
        inputPins.get(pin).addListener((GpioPinListenerDigital) event ->
                listener.accept(event.getState().isHigh()));
    }
}
