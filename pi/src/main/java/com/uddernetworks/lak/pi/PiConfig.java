package com.uddernetworks.lak.pi;

import com.uddernetworks.lak.api.PiDetails;
import com.uddernetworks.lak.api.PiManager;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.pi.button.CaseButtonFactory;
import com.uddernetworks.lak.pi.button.GPIOAbstractedButton;
import com.uddernetworks.lak.pi.button.GPIOButtonHandler;
import com.uddernetworks.lak.pi.gpio.PinController;
import com.uddernetworks.lak.pi.light.GPIOAbstractedLight;
import com.uddernetworks.lak.pi.light.GPIOLightHandler;
import com.uddernetworks.lak.pi.light.LightFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is a configuration explicitly defining implementations from the <pre>pi-api</pre> library to be used
 * in client applications.
 */
@Configuration
public class PiConfig {

    @Bean
    PiDetails piDetails() {
        return new AutoPiDetails();
    }

    @Bean
    PiManager piManager(@Qualifier("gpioPinController") PinController pinController,
                        @Autowired ButtonHandler<GPIOAbstractedButton> buttonHandler,
                        @Autowired LightHandler<GPIOAbstractedLight> lightHandler,
                        @Qualifier("caseButtonFactory") CaseButtonFactory caseButtonFactory,
                        @Qualifier("lightFactory") LightFactory lightFactory) {
        return new PiZeroManager(pinController, buttonHandler, lightHandler, caseButtonFactory, lightFactory);
    }

    @Bean
    ButtonHandler buttonHandler(@Qualifier("gpioPinController") PinController pinController) {
        return new GPIOButtonHandler(pinController);
    }

    @Bean
    LightHandler lightHandler(@Qualifier("gpioPinController") PinController pinController) {
        return new GPIOLightHandler(pinController);
    }

}
