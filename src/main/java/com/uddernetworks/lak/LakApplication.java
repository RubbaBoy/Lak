package com.uddernetworks.lak;

import com.uddernetworks.lak.hardware.ButtonInterface;
import com.uddernetworks.lak.keys.KeyboardInput;
import com.uddernetworks.lak.api.PiManager;
import com.uddernetworks.lak.api.button.AbstractedButton;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.button.ButtonId;
import com.uddernetworks.lak.api.light.AbstractedLight;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.api.light.LightId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static com.uddernetworks.lak.Utility.readResourceString;

@SpringBootApplication(scanBasePackages = {"com", "com.uddernetworks.lak.pi", "com.uddernetworks.lak"})
public class LakApplication implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LakApplication.class);

    private final KeyboardInput keyboardInput;
    private final PiManager piManager;
    private final ButtonInterface buttonInterface;
    private final ButtonHandler<AbstractedButton> buttonHandler;
    private final LightHandler<AbstractedLight> lightHandler;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public LakApplication(@Qualifier("devEventKeyboardInput") KeyboardInput keyboardInput,
                          PiManager piManager,
                          ButtonInterface buttonInterface,
                          ButtonHandler<AbstractedButton> buttonHandler,
                          LightHandler<AbstractedLight> lightHandler) {
        this.keyboardInput = keyboardInput;
        this.piManager = piManager;
        this.buttonInterface = buttonInterface;
        this.buttonHandler = buttonHandler;
        this.lightHandler = lightHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(LakApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.debug("Registering and listening to components...");

        piManager.init();
        piManager.startListening();

        LOGGER.debug("Initializing button interface");
        buttonInterface.init();

        LOGGER.info("Listening for keyboard events...");
        keyboardInput.startListening();
    }
}
