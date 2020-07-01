package com.uddernetworks.lak;

import com.uddernetworks.lak.keys.KeyboardInput;
import com.uddernetworks.lak.pi.api.PiManager;
import com.uddernetworks.lak.pi.api.button.AbstractedButton;
import com.uddernetworks.lak.pi.api.button.ButtonHandler;
import com.uddernetworks.lak.pi.api.button.ButtonId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication(scanBasePackages = {"com.uddernetworks.lak"})
public class LakApplication implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LakApplication.class);

    private final KeyboardInput keyboardInput;
    private final PiManager piManager;
    private final ButtonHandler<AbstractedButton> buttonHandler;

    public LakApplication(@Qualifier("devEventKeyboardInput") KeyboardInput keyboardInput,
                          PiManager piManager,
                          ButtonHandler<AbstractedButton> buttonHandler) {
        this.keyboardInput = keyboardInput;
        this.piManager = piManager;
        this.buttonHandler = buttonHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(LakApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.debug("Registering and listening to components...");
        piManager.registerComponents();
        piManager.startListening();

        var redButton = buttonHandler.buttonFromId(ButtonId.RED);
        LOGGER.debug("Button = {}", redButton);

        LOGGER.info("Listening for keyboard events...");
        keyboardInput.startListening();
    }
}
