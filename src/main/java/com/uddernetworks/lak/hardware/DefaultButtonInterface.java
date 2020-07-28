package com.uddernetworks.lak.hardware;

import com.uddernetworks.lak.api.button.AbstractedButton;
import com.uddernetworks.lak.api.button.ButtonHandler;
import com.uddernetworks.lak.api.button.ButtonId;
import com.uddernetworks.lak.api.light.AbstractedLight;
import com.uddernetworks.lak.api.light.LightHandler;
import com.uddernetworks.lak.api.light.LightId;
import com.uddernetworks.lak.keys.KeyboardInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("defaultButtonInterface")
public class DefaultButtonInterface implements ButtonInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultButtonInterface.class);

    private final KeyboardInterceptor keyboardInterceptor;
    private final ButtonHandler<AbstractedButton> buttonHandler;
    private final LightHandler<AbstractedLight> lightHandler;

    private RecordingStatus recordingStatus = new RecordingStatus(false, null, null);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DefaultButtonInterface(@Qualifier("soundKeyboardInterceptor") KeyboardInterceptor keyboardInterceptor,
                                  ButtonHandler<AbstractedButton> buttonHandler,
                                  LightHandler<AbstractedLight> lightHandler) {
        this.keyboardInterceptor = keyboardInterceptor;
        this.buttonHandler = buttonHandler;
        this.lightHandler = lightHandler;
    }

    /**
     * Initializes the physical buttons. The following is a description of what each button and its respective light
     * does.
     *
     * <br><br><b>Green Button</b><br>
     * When pressed down, sound is active and the light is on. When up, sound is disabled and the light is off.
     *
     * <br><br><b>Red Button</b><br>
     * TODO
     *
     * <br><br><b>Blue Button</b><br>
     * When down, the light is on and sound is being recorded by the aux in. When up, recording is stopped and the
     * light is off. If the system is not ready for recording, nothing happens.
     */
    @Override
    public void init() {

        // TODO: Remove initial sequence

        LOGGER.debug("Toggling lights on");
        lightHandler.getLights().forEach(light -> {
            light.setStatus(true);
            sleep(1000);
        });

        LOGGER.debug("Toggling lights off");
        sleep(2000);

        lightHandler.getLights().forEach(light -> {
            light.setStatus(false);
            sleep(1000);
        });

        // End initial sequence  >>=

        LOGGER.debug("Red = {}", buttonHandler.buttonFromId(ButtonId.RED));

        buttonHandler.buttonFromId(ButtonId.RED)
                .ifPresent(button -> {
                    LOGGER.debug("Button precent");
                    button.setListener(pressed -> {
                        LOGGER.debug("Red pressed!");
                        lightHandler.lightFromId(LightId.RED_BUTTON)
                                .ifPresent(light -> light.setStatus(pressed));
                    });
                });

        buttonHandler.buttonFromId(ButtonId.GREEN)
                .ifPresent(button -> {
                    keyboardInterceptor.setSoundEnabled(button.isPressed());
                    button.setListener(pressed -> {
                        keyboardInterceptor.setSoundEnabled(pressed);
                        lightHandler.lightFromId(LightId.GREEN_BUTTON)
                                .ifPresent(light -> light.setStatus(pressed));
                    });
                });

        var blueLight = lightHandler.lightFromId(LightId.BLUE_BUTTON);
        buttonHandler.buttonFromId(ButtonId.BLUE)
                .ifPresent(button -> {
                    button.setListener(pressed -> {
                        if (pressed && recordingStatus.isReady()) {
                            LOGGER.debug("Starting the recording");

                            recordingStatus.start();
                            blueLight.ifPresent(light -> light.setStatus(true));
                        }

                        if (!pressed && !recordingStatus.isReady() && recordingStatus.isRecording()) {
                            LOGGER.debug("Stopping the recording");

                            recordingStatus.stop();
                            blueLight.ifPresent(light -> light.setStatus(false));
                        }
                    });
                });
    }

    @Override
    public void startRecording(Runnable start, Runnable stop) {
        if (!recordingStatus.isReady()) {
            LOGGER.info("Press the blue button down to start recording!");

            var lightOptional = lightHandler.lightFromId(LightId.BLUE_BUTTON);
            lightOptional.ifPresent(light -> light.pulse(750));

            recordingStatus = new RecordingStatus(true, start, stop);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }

    static class RecordingStatus {
        private boolean ready;
        private boolean recording;
        private final Runnable start;
        private final Runnable stop;

        RecordingStatus(boolean ready, Runnable start, Runnable stop) {
            this.ready = ready;
            this.start = start;
            this.stop = stop;
        }

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }

        public boolean isRecording() {
            return recording;
        }

        public void setRecording(boolean recording) {
            this.recording = recording;
        }

        public void start() {
            ready = false;
            recording = true;
            if (start != null) {
                start.run();
            }
        }

        public void stop() {
            recording = false;
            if (stop != null) {
                stop.run();
            }
        }
    }
}
