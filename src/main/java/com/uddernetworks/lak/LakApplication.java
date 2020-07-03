package com.uddernetworks.lak;

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
    private final ButtonHandler<AbstractedButton> buttonHandler;
    private final LightHandler<AbstractedLight> lightHandler;

    public LakApplication(@Qualifier("devEventKeyboardInput") KeyboardInput keyboardInput,
                          PiManager piManager,
                          ButtonHandler<AbstractedButton> buttonHandler,
                          LightHandler<AbstractedLight> lightHandler) {
        this.keyboardInput = keyboardInput;
        this.piManager = piManager;
        this.buttonHandler = buttonHandler;
        this.lightHandler = lightHandler;
    }

//    public LakApplication() throws ClassNotFoundException, IOException {
//        System.out.println("BREUHHHHHH");
////        System.out.println(Class.forName("com.uddernetworks.lak.api.PiManager"));
////        System.out.println(Class.forName("com.uddernetworks.lak.last.Last"));
////        System.out.println(Class.forName("com.uddernetworks.lak.pi.Hmm"));
////        System.out.println(Class.forName("com.uddernetworks.lak.pi.Twooo"));
//
//        System.out.println("============");
//
//
//        // create scanner and disable default filters (that is the 'false' argument)
//        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
//// add include filters which matches all the classes (or use your own)
//        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
//
//// get matching classes defined in the package
//        final Set<BeanDefinition> classes = provider.findCandidateComponents("com.uddernetworks.lak");
//
//// this is how you can load the class type from BeanDefinition instance
//        for (BeanDefinition bean: classes) {
//            Class<?> clazz = Class.forName(bean.getBeanClassName());
//            System.out.println(clazz.getCanonicalName());
//            // ... do your magic with the class ...
//        }
//
//        System.out.println("============");
//
////        System.out.println(Class.forName("com.uddernetworks.lak.pi.PiZeroManager"));
//
//        keyboardInput = null;
//                piManager = null;
//        buttonHandler = null;
//                lightHandler = null;
//    }

    public static void main(String[] args) {
        SpringApplication.run(LakApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LOGGER.debug("Registering and listening to components...");
        piManager.init();
        piManager.startListening();

        var buttonLights = Map.of(
                ButtonId.RED, LightId.RED_BUTTON,
                ButtonId.GREEN, LightId.GREEN_BUTTON,
                ButtonId.BLUE, LightId.BLUE_BUTTON
        );

        buttonHandler.getButtons().forEach(button ->
                button.setListener(pressed -> {
                    LOGGER.info("[{}] Pressed: {}", button.getName(), pressed);
                    lightHandler.lightFromId(buttonLights.get(button.getId().getId())).ifPresent(light ->
                            light.setStatus(pressed));
                }));

        var redButton = buttonHandler.buttonFromId(ButtonId.RED);
        LOGGER.debug("Button = {}", redButton);

        LOGGER.info("Listening for keyboard events...");
        keyboardInput.startListening();
    }
}
