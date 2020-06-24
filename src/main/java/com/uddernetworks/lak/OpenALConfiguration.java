package com.uddernetworks.lak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.urish.openal.ALException;
import org.urish.openal.OpenAL;

@Configuration
public class OpenALConfiguration {

    @Bean
    public OpenAL openAL() throws ALException {
        return new OpenAL();
    }
}
