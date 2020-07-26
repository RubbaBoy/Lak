package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

@Component("cachedJSynPool")
public class CachedJSynPool implements JSynPool {

    private final Synthesizer synth;
    private final LineOut lineOut;
    private static final ExecutorService POOL = Executors.newCachedThreadPool();

    public CachedJSynPool() {
        synth = JSyn.createSynthesizer();
        synth.add(lineOut = new LineOut());
        synth.start();
    }

    @Override
    public void provisionAsyncSynth(BiConsumer<Synthesizer, LineOut> synthConsumer) {
        POOL.submit(() -> synthConsumer.accept(synth, lineOut));
    }
}
