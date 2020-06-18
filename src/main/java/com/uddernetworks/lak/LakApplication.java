package com.uddernetworks.lak;

import com.uddernetworks.lak.keys.KeyboardInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.urish.openal.ALException;
import org.urish.openal.OpenAL;

@SpringBootApplication(scanBasePackages = {"com"})
public class LakApplication implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LakApplication.class);

    private final KeyboardInput keyboardInput;

    public LakApplication(@Qualifier("devEventKeyboardInput") KeyboardInput keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    public static void main(String[] args) {
        SpringApplication.run(LakApplication.class, args);
    }

    @Bean
    public OpenAL openAL() throws ALException {
        return new OpenAL();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.info("Ready!!!!");

        keyboardInput.startListening();
    }
}
