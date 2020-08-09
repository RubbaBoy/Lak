package com.uddernetworks.lak.pi.gpio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("gpioPinHandler")
public class GPIOPinHandler implements PinHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GPIOPinHandler.class);

    @Override
    public void exportPin(int pin) {
        if (!Files.exists(getPinPath(pin))) {
            sendData(getGPIOPath("export"), pin);
        }
    }

    @Override
    public void unexportPin(int pin) {
        if (Files.exists(getPinPath(pin))) {
            sendData(getGPIOPath("unexport"), pin);
        }
    }

    @Override
    public void setDirection(int pin, PinDirection pinDirection) {
        sendData(getPinPath(pin, "direction"), pinDirection.getDirection());
    }

    @Override
    public void setValue(int pin, PinValue value) {
        sendData(getPinPath(pin, "value"), value.getValue());
    }

    @Override
    public PinValue readValue(int pin) {
        try {
            var bytes = Files.readAllBytes(getPinPath(pin, "value"));

            if (bytes.length != 2) {
                return PinValue.LOW;
            }

            return bytes[0] == 48 ? PinValue.LOW : PinValue.HIGH;
        } catch (IOException e) {
            LOGGER.error("Error reading pin's value", e);
            return PinValue.LOW;
        }
    }

    @Override
    public void setEdge(int pin, PinEdge pinEdge) {
        sendData(getPinPath(pin, "edge"), pinEdge.getEdge());
    }

    @Override
    public void setActiveLow(int pin, PinActiveLow pinActiveLow) {
        sendData(getPinPath(pin, "active_low"), pinActiveLow.getValue());
    }

    private void sendData(Path path, Object data) {
        try {
            Files.write(path, data.toString().getBytes());
        } catch (IOException e) {
            LOGGER.error("Error writing to " + path, e);
        }
    }

    private Path getGPIOPath(String child) {
        return Paths.get("/sys/class/gpio", child);
    }

    private Path getPinPath(int pin) {
        return Paths.get("/sys/class/gpio/gpio" + pin);
    }

    private Path getPinPath(int pin, String child) {
        return getPinPath(pin).resolve(child);
    }
}
