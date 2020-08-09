package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.uddernetworks.lak.concurrency.BiCompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import static com.jsyn.engine.SynthesisEngine.DEFAULT_FRAME_RATE;

@Component("cachedJSynPool")
public class CachedJSynPool implements JSynPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedJSynPool.class);

    private final Synthesizer synth;
    private final LineOut lineOut;
    private static final ExecutorService POOL = Executors.newCachedThreadPool();

    public CachedJSynPool() {
        synth = JSyn.createSynthesizer();

        LOGGER.debug("Finding headphones...");
        var audioManager = synth.getAudioDeviceManager();
        var headphones = IntStream.range(0, audioManager.getDeviceCount()).filter(i -> audioManager.getDeviceName(i).startsWith("Headphones")).findFirst();
        headphones.ifPresentOrElse(i -> LOGGER.debug("Using Headphones device #{}", i), () -> LOGGER.debug("Couldn't find headphones, using default audio device"));

        synth.add(lineOut = new LineOut());
        synth.start(DEFAULT_FRAME_RATE, -1, 0, headphones.orElse(-1), 2);
    }

    @Override
    public BiCompletableFuture<Synthesizer, LineOut> provisionAsyncSynth() {
        var completableFuture = new BiCompletableFuture<Synthesizer, LineOut>();
        provisionAsyncSynth(completableFuture::complete);
        return completableFuture;
    }

    @Override
    public void provisionAsyncSynth(BiConsumer<Synthesizer, LineOut> synthConsumer) {
        POOL.submit(() -> synthConsumer.accept(synth, lineOut));
    }
}
