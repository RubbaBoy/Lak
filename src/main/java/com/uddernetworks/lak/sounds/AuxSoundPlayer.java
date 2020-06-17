package com.uddernetworks.lak.sounds;

import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.keys.KeyboardInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;

@Component("auxSoundPlayer")
public class AuxSoundPlayer implements SoundPlayer, LineListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuxSoundPlayer.class);

    private final KeyManager keyManager;
    private final SoundManager soundManager;

    private boolean playCompleted;

    public AuxSoundPlayer(@Qualifier("defaultKeyManager") KeyManager keyManager,
                          @Qualifier("variableSoundManager") SoundManager soundManager) {
        this.keyManager = keyManager;
        this.soundManager = soundManager;
    }

    @Override
    public void playSound(KeyEnum keyEnum) {
        var key = keyManager.getKeyFrom(keyEnum);
        var soundVariant = key.getSound();
        var sound = soundVariant.getSound();
        LOGGER.debug("Playing sound {}", sound.getId());
        // TODO: Copied from the internet, modify this and make it work better. This is only a placeholder.

        try {
            var soundLocation = soundVariant.getSound().getURI().toURL();

            var audioStream = AudioSystem.getAudioInputStream(soundLocation);

            var format = audioStream.getFormat();

            var info = new DataLine.Info(Clip.class, format);

            var audioClip = (Clip) AudioSystem.getLine(info);

            for (var modulator : soundVariant.getModulators()) {
                var modulatedSound = modulator.modulateSound(format, audioClip);
                if (modulatedSound != null) {
                    format = modulatedSound;
                }
            }

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();

            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();
        } catch (MalformedURLException e) {
            LOGGER.error("Invalid audio URL", e);
        } catch (LineUnavailableException e) {
            LOGGER.error("Audio line for playback is unavailable", e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error("The specific audio file is not supported", e);
        }

    }

    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
    }
}
