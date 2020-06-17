package com.uddernetworks.lak.keys;

import com.uddernetworks.lak.sounds.SoundPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("soundKeyboardInterceptor")
public class SoundKeyboardInterceptor implements KeyboardInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundKeyboardInterceptor.class);

    private final SoundPlayer soundPlayer;
    private final KeyboardOutput keyboardOutput;

    public SoundKeyboardInterceptor(@Qualifier("auxSoundPlayer") SoundPlayer soundPlayer,
                                    @Qualifier("usbKeyboardOutput") KeyboardOutput keyboardOutput) {
        this.soundPlayer = soundPlayer;
        this.keyboardOutput = keyboardOutput;
    }

    @Override
    public void receiveKey(KeyEnum keyEnum) {
        LOGGER.debug("Playing sound for {}", keyEnum);
        soundPlayer.playSound(keyEnum);
        keyboardOutput.outputKey(keyEnum);
    }

}
