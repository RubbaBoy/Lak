package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.Synthesizer;
import com.jsyn.data.AudioSample;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface PlayerPoolManager {

    /**
     * Manages multiple {@link PlayerPool}s internally, creating them based off of need by the given {@link AudioSample}.
     *
     * @param synth The associated {@link Synthesizer}
     * @param sample The {@link AudioSample}
     * @param out The {@link LineOut}
     * @return The {@link CompletableFuture} accepting {@link VariableRateDataReader}s asynchronously
     */
    CompletableFuture<VariableRateDataReader> provisionPlayer(Synthesizer synth, AudioSample sample, LineOut out);

    /**
     * Manages multiple {@link PlayerPool}s internally, creating them based off of need by the given {@link AudioSample}.
     *
     * @param synth The associated {@link Synthesizer}
     * @param sample The {@link AudioSample}
     * @param out The {@link LineOut}
     * @param playerConsumer The consumer accepting {@link VariableRateDataReader}s asynchronously
     */
    void provisionPlayer(Synthesizer synth, AudioSample sample, LineOut out, Consumer<VariableRateDataReader> playerConsumer);

}
