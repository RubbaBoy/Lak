package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.pi.api.PiDetails;
import com.uddernetworks.lak.pi.api.PiManager;
import com.uddernetworks.lak.pi.api.button.AbstractedButton;
import com.uddernetworks.lak.pi.api.button.ButtonHandler;
import com.uddernetworks.lak.pi.api.light.AbstractedLight;
import com.uddernetworks.lak.pi.api.light.LightHandler;
import com.uddernetworks.lak.pi.button.CaseButtonFactory;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import com.uddernetworks.lak.pi.button.GPIOButtonHandler;
import com.uddernetworks.lak.pi.light.GPIOAbstractedLight;
import com.uddernetworks.lak.pi.light.GPIOLightHandler;
import com.uddernetworks.lak.pi.light.LightFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PiConfig {

    public PiConfig() {
        System.out.println("Constructorrrr");
    }

    @Bean
    PiDetails piDetails() {
        return new AutoPiDetails();
    }

    @Bean
    PiManager piManager(@Autowired ButtonHandler<GPIOAbstractedButton> buttonHandler,
                        @Autowired LightHandler<GPIOAbstractedLight> lightHandler,
                        @Qualifier("caseButtonFactory") CaseButtonFactory caseButtonFactory,
                        @Qualifier("lightFactory") LightFactory lightFactory) {
        return new PiZeroManager(buttonHandler, lightHandler, caseButtonFactory, lightFactory);
    }

    @Bean
    ButtonHandler buttonHandler() {
        return new GPIOButtonHandler();
    }

    @Bean
    LightHandler lightHandler() {
        return new GPIOLightHandler();
    }

}
