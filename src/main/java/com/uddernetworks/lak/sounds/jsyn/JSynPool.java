package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.uddernetworks.lak.concurrency.BiCompletableFuture;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface JSynPool {

    /**
     * Provisions a {@link Synthesizer} instance, closing it when it is done.
     *
     * @return The {@link BiCompletableFuture} to consume the generated {@link Synthesizer}
     */
    BiCompletableFuture<Synthesizer, LineOut> provisionAsyncSynth();

    /**
     * Provisions a {@link Synthesizer} instance, closing it when it is done.
     *
     * @param synthConsumer The consumer to consume the generated {@link Synthesizer}
     */
    void provisionAsyncSynth(BiConsumer<Synthesizer, LineOut> synthConsumer);

}
