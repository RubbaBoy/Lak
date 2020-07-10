package com.uddernetworks.lak.sounds.input;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.LineIn;
import com.jsyn.util.WaveRecorder;
import com.uddernetworks.lak.sounds.jsyn.JSynPool;
import com.uddernetworks.lak.sounds.SoundManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Component("auxSoundInput")
public class AuxSoundInput implements SoundInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuxSoundInput.class);

    private final JSynPool jSynPool;
    private final SoundManager soundManager;

    private boolean recording;
    private String recordingName;
    private Synthesizer synth;
    private WaveRecorder recorder;
    private Path recordingPath;

    public AuxSoundInput(@Qualifier("cachedJSynPool") JSynPool jSynPool,
                         @Qualifier("variableSoundManager") SoundManager soundManager) {
        this.jSynPool = jSynPool;
        this.soundManager = soundManager;
    }

    @Override
    public void startRecording(String name) throws IOException {
        synth = JSyn.createSynthesizer();
        recordingPath = soundManager.convertSoundPath(name.replaceAll("[^a-zA-Z0-9]", "") + ".wav");
        recorder = new WaveRecorder(synth, recordingPath.toFile());

        LOGGER.debug("Recording {} to {}", name, recordingPath.toAbsolutePath());

        LineIn lineIn;
        synth.add(lineIn = new LineIn());
        lineIn.output.connect(0, recorder.getInput(), 0);
        lineIn.output.connect(1, recorder.getInput(), 1);

        synth.start(44100, AudioDeviceManager.USE_DEFAULT_DEVICE, 2, AudioDeviceManager.USE_DEFAULT_DEVICE, 2);

        recorder.start();
    }

    @Override
    public Path stopRecording() throws IOException {
        recorder.stop();
        recorder.close();
        synth.stop();
        return recordingPath;
    }
}
