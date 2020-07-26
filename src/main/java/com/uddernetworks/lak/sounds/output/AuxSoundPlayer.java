package com.uddernetworks.lak.sounds.output;

import com.jsyn.Synthesizer;
import com.jsyn.data.AudioSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.uddernetworks.lak.keys.KeyEnum;
import com.uddernetworks.lak.keys.KeyManager;
import com.uddernetworks.lak.sounds.jsyn.JSynPool;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.source.SoundSourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auxSoundPlayer")
public class AuxSoundPlayer implements SoundPlayer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuxSoundPlayer.class);

    private final KeyManager keyManager;
    private final SoundManager soundManager;
    private final SoundSourceManager soundSourceManager;
    private final JSynPool jSynPool;

    public AuxSoundPlayer(@Qualifier("defaultKeyManager") KeyManager keyManager,
                          @Qualifier("variableSoundManager") SoundManager soundManager,
                          @Qualifier("cachedSoundSourceManager") SoundSourceManager soundSourceManager,
                          @Qualifier("cachedJSynPool") JSynPool jSynPool) {
        this.keyManager = keyManager;
        this.soundManager = soundManager;
        this.soundSourceManager = soundSourceManager;
        this.jSynPool = jSynPool;
    }

    @Override
    public void playSound(KeyEnum keyEnum) {
        var key = keyManager.getKeyFrom(keyEnum);
        var soundVariant = key.getSound();
        var sound = soundVariant.getSound();

        LOGGER.debug("Playing sound {}", sound.getId());

        var sourceOptional = soundSourceManager.getOrCreate(soundVariant);
        if (sourceOptional.isEmpty()) {
            LOGGER.error("Sound for {} unavailable, skipping", soundVariant);
            return;
        }

        var sample = sourceOptional.get();

        jSynPool.provisionAsyncSynth((synth, lineOut) -> {
            try {
                var samplePlayerOptional = createPlayer(synth, sample, lineOut);
                if (samplePlayerOptional.isEmpty()) {
                    LOGGER.error("Sound player for {} unavailable, skipping", soundVariant);
                    return;
                }

                var player = samplePlayerOptional.get();

                // The rate that can be changed
                player.rate.set(sample.getFrameRate());

                LOGGER.debug("Synth going with {} mods", soundVariant.getModulators());
                for (var modulator : soundVariant.getModulators()) {
                    LOGGER.debug("Modding with {}", modulator);
                    modulator.modulateSound(synth, player);
                }

                // We only need to start the LineOut. It will pull data from the
                // sample player.
                lineOut.start();

                if (sample.getSustainBegin() < 0) {
                    player.dataQueue.queue(sample);
                } else {
                    player.dataQueue.queueOn(sample);
                    synth.sleepFor(2.0);
                    player.dataQueue.queueOff(sample);
                }

                do {
                    synth.sleepFor(1);
                } while (player.dataQueue.hasMore());
            } catch (Exception e) {
                LOGGER.error("An error has occurred while playing a sound for key " + keyEnum, e);
            }
        });
    }

    private Optional<VariableRateDataReader> createPlayer(Synthesizer synth, AudioSample sample, LineOut lineOut) {
        VariableRateDataReader player;
        if (sample.getChannelsPerFrame() == 1) {
            synth.add(player = new VariableRateMonoReader());
            player.output.connect(0, lineOut.input, 0);
        } else if (sample.getChannelsPerFrame() == 2) {
            synth.add(player = new VariableRateStereoReader());
            player.output.connect(0, lineOut.input, 0);
            player.output.connect(1, lineOut.input, 1);
        } else {
            LOGGER.error("Can only play mono or stereo samples.");
            return Optional.empty();
        }

        return Optional.of(player);
    }
}
