package com.uddernetworks.lak.sounds.jsyn;

import com.jsyn.Synthesizer;
import com.jsyn.data.AudioSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * A lazy pool, creating a background thread when needed, and only making {@link VariableRateDataReader}s if none are
 * available and is under 10 total. Each lazy pool is associated with one {@link AudioSample}.
 */
public class LazyPlayerPool implements PlayerPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(LazyPlayerPool.class);

    private static final int THREADS = 10;

    private final Semaphore SEMAPHORE = new Semaphore(THREADS);
    private final JSynContainer[] CONTAINERS = new JSynContainer[THREADS];
    private final BlockingQueue<Consumer<VariableRateDataReader>> QUEUE = new LinkedBlockingQueue<>();
    private final AtomicBoolean backgroundStarted = new AtomicBoolean();
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS + 1);

    private final Synthesizer synth;
    private final AudioSample sample;
    private final LineOut out;

    public LazyPlayerPool(Synthesizer synth, AudioSample sample, LineOut out) {
        this.synth = synth;
        this.sample = sample;
        this.out = out;
    }

    private void startBackground() {
        executorService.submit(() -> {
            try {
                while (true) {
                    var take = QUEUE.take();
                    SEMAPHORE.acquire();

                    executorService.submit(() -> {
                        var container = getContainer();

                        take.accept(container.getPlayer());

                        container.setActive(false);
                        SEMAPHORE.release();
                    });
                }
            } catch (InterruptedException e) {
                LOGGER.error("Error with semaphore", e);
            }
        });
    }

    private JSynContainer getContainer() {
        for (int i = 0; i < CONTAINERS.length; i++) {
            var container = CONTAINERS[i];

            if (container == null) {
                container = (CONTAINERS[i] = new JSynContainer(createPlayer(synth, sample, out).orElseThrow()));
            }

            if (!container.isActive()) {
                container.setActive(true);
                return container;
            }
        }

        throw new RuntimeException("Unsynchronization between containers and semaphore");
    }

    @Override
    public void provisionPlayer(Consumer<VariableRateDataReader> synthConsumer) {
        if (!backgroundStarted.getAndSet(true)) {
            startBackground();
        }

        QUEUE.offer(synthConsumer);
    }

    private static Optional<VariableRateDataReader> createPlayer(Synthesizer synth, AudioSample sample, LineOut lineOut) {
        VariableRateDataReader player;
        if (sample.getChannelsPerFrame() == 1) {
            synth.add(player = new VariableRateMonoReader());
            player.output.connect(0, lineOut.input, 0);
        } else if (sample.getChannelsPerFrame() == 2) {
            synth.add(player = new VariableRateStereoReader());
            player.output.connect(0, lineOut.input, 0);
            player.output.connect(1, lineOut.input, 1);
        } else {
            return Optional.empty();
        }

        return Optional.of(player);
    }

    static class JSynContainer {
        private final VariableRateDataReader player;
        private final AtomicBoolean active = new AtomicBoolean();

        JSynContainer(VariableRateDataReader player) {
            this.player = player;
        }

        public VariableRateDataReader getPlayer() {
            return player;
        }

        public boolean isActive() {
            return active.get();
        }

        public void setActive(boolean active) {
            this.active.set(active);
        }
    }

}
