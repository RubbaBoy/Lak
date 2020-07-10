package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component("cachedJSynPool")
public class CachedJSynPool implements JSynPool {

    private static final Executor JSYN_POOL = Executors.newCachedThreadPool();

    @Override
    public void provisionAsyncSynth(Consumer<Synthesizer> synthConsumer) {
        JSYN_POOL.execute(() -> {
            var synth = JSyn.createSynthesizer();
            try {
                synthConsumer.accept(synth);
            } finally {
                synth.stop();
            }
        });
    }
}
