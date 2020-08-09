package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.unitgen.VariableRateDataReader;

import java.util.function.Consumer;


public interface PlayerPool {

    /**
     * Provisions a cached, unused {@link VariableRateDataReader} on another thread. This is to reduce overhead.
     *
     * @param playerConsumer The consumer accepting asynchronously a {@link VariableRateDataReader}
     */
    void provisionPlayer(Consumer<VariableRateDataReader> playerConsumer);

}
