package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.api.PiDetails;
import com.uddernetworks.lak.api.PiManager;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.pi.light.DummyLightHandler;
import com.uddernetworks.lak.pi.button.DummyButtonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyConfig {

    @Bean
    PiDetails emptyPiDetails() {
        return new EmptyPiDetails();
    }

    @Bean
    PiManager piManager() {
        return new DummyManager();
    }

    @Bean
    ButtonHandler<?> buttonHandler() {
        return new DummyButtonHandler();
    }

    @Bean
    LightHandler<?> lightHandler() {
        return new DummyLightHandler();
    }

}
