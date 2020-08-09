package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.Synthesizer;
import com.jsyn.data.AudioSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Component("cachedPlayerPoolManager")
public class LazyPlayerPoolManager implements PlayerPoolManager {

    private final Map<AudioSample, PlayerPool> samplePoolMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public CompletableFuture<VariableRateDataReader> provisionPlayer(Synthesizer synth, AudioSample sample, LineOut out) {
        var completer = new CompletableFuture<VariableRateDataReader>();
        provisionPlayer(synth, sample, out, completer::complete);
        return completer;
    }

    @Override
    public void provisionPlayer(Synthesizer synth, AudioSample sample, LineOut out, Consumer<VariableRateDataReader> playerConsumer) {
        samplePoolMap.computeIfAbsent(sample, $ -> new LazyPlayerPool(synth, sample, out)).provisionPlayer(playerConsumer);
    }
}
