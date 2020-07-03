package com.uddernetworks.lak;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.urish.openal.ALException;
import org.urish.openal.OpenAL;

//@PropertySource("classpath:application.properties")
@Configuration
public class OpenALConfiguration {

    @Bean
    public OpenAL openAL() throws ALException {
        return new OpenAL();
    }

//    @Bean
//    PiManager piManager() {
//        System.out.println("bruhhhh");
//        System.out.println(Shit.huh());
//        System.out.println(WTF.wtf());
//        System.out.println(PiZeroManager.bruh());
////        System.out.println(Bruh2.huh2());
////        System.out.println(Coooo.okok());
////        System.out.println(Ok.okok());
////        System.out.println(Ok2.okok());
////        System.out.println(Ok3.okok());
////        System.out.println(Ok4.okok());
////        System.out.println(PiZeroManager.huh());
////        return new PiZeroManager(null, null, null, null, null);
//        return null;
//    }

//    class Huh implements PiManager {
//
//        @Override
//        public void init() {
//
//        }
//
//        @Override
//        public void startListening() {
//
//        }
//    }

//    @Bean
//    PiManager piManager() {
//        System.out.println("bruhhhh2222222");
//        return null;
//    }
}
