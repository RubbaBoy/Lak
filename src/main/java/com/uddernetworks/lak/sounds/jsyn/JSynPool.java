package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.Synthesizer;

import java.util.function.Consumer;

public interface JSynPool {

    /**
     * Provisions a {@link Synthesizer} instance, closing it when it is done.
     *
     * @param synthConsumer The consumer to consume the generated {@link Synthesizer}
     */
    void provisionAsyncSynth(Consumer<Synthesizer> synthConsumer);

}
