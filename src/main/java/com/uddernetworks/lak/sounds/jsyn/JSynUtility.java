package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.Synthesizer;
import com.jsyn.data.AudioSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;

public class JSynUtility {

    public static void startLineOut(Synthesizer synth, AudioSample sample, LineOut lineOut, VariableRateDataReader player) throws InterruptedException {
        lineOut.start();

        if (sample.getSustainBegin() < 0) {
            player.dataQueue.queue(sample);
        } else {
            player.dataQueue.queueOn(sample);
            synth.sleepFor(2.0);
            player.dataQueue.queueOff(sample);
        }

        do {
            synth.sleepFor(0.1);
        } while (player.dataQueue.hasMore());
    }

}
