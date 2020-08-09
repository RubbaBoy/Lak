package com.uddernetworks.lak.sounds.tts;

import com.jsyn.data.AudioSample;
import com.jsyn.util.SampleLoader;
import com.uddernetworks.lak.Utility;
import com.uddernetworks.lak.sounds.jsyn.JSynPool;
import com.uddernetworks.lak.sounds.jsyn.PlayerPoolManager;
import com.uddernetworks.lak.sounds.source.SoundSourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.uddernetworks.lak.Utility.readResource;
import static com.uddernetworks.lak.sounds.jsyn.JSynUtility.startLineOut;

@Component("wavIPSpeaker")
public class WavIPSpeaker implements IPSpeaker {

    private static final Logger LOGGER = LoggerFactory.getLogger(WavIPSpeaker.class);

    private final SoundSourceManager soundSourceManager;
    private final JSynPool jSynPool;
    private final PlayerPoolManager playerPoolManager;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Map<Character, AudioSample> soundSamples = new HashMap<>();

    public WavIPSpeaker(@Qualifier("cachedSoundSourceManager") SoundSourceManager soundSourceManager,
                        @Qualifier("cachedJSynPool") JSynPool jSynPool,
                        @Qualifier("cachedPlayerPoolManager") PlayerPoolManager playerPoolManager) {
        this.soundSourceManager = soundSourceManager;
        this.jSynPool = jSynPool;
        this.playerPoolManager = playerPoolManager;

        Utility.<Character, String>bigMapOf(
                '0', "0.wav",
                '1', "1.wav",
                '2', "2.wav",
                '3', "3.wav",
                '4', "4.wav",
                '5', "5.wav",
                '6', "6.wav",
                '7', "7.wav",
                '8', "8.wav",
                '9', "9.wav",
                '.', "dot.wav",
                ':', "colon.wav"
        ).forEach((character, file) -> {
            try {
                soundSamples.put(character, SampleLoader.loadFloatSample(new ByteArrayInputStream(readResource("tts/" + file))));
            } catch (IOException e) {
                LOGGER.error("An error occurred while reading TTS file tts/" + file, e);
            }
        });
    }

    @Override
    public CompletableFuture<Void> speakIP(InetAddress address) {
        return CompletableFuture.runAsync(() -> {
            var speaking = address.getHostAddress();
            LOGGER.debug("Speaking '{}'", speaking);

            jSynPool.provisionAsyncSynth()
                    .thenAccept((synth, lineOut) ->
                            speaking.chars()
                                    .mapToObj(i -> (char) i)
                                    .map(soundSamples::get)
                                    .filter(Objects::nonNull)
                                    .forEach(sample ->
                                            playerPoolManager.provisionPlayer(synth, sample, lineOut)
                                                    .thenAccept(player -> {
                                                        player.rate.set(sample.getFrameRate());

                                                        try {
                                                            startLineOut(synth, sample, lineOut, player);
                                                        } catch (InterruptedException e) {
                                                            LOGGER.error("An error occurred while playing TTS sound", e);
                                                        }
                                                    }).join()))
                    .join();
        }, executorService);
    }
}
