package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface JSynPool {

    /**
     * Provisions a {@link Synthesizer} instance, closing it when it is done.
     *
     * @param synthConsumer The consumer to consume the generated {@link Synthesizer}
     */
    void provisionAsyncSynth(BiConsumer<Synthesizer, LineOut> synthConsumer);

}
