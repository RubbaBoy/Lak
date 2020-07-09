package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.database.sound.SoundRepository;
import com.uddernetworks.lak.sounds.SoundVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.uddernetworks.lak.database.DatabaseUtility.waitFuture;

@Component("defaultModulationManager")
public class DefaultModulationManager implements ModulationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultModulationManager.class);

    @Value("${lak.database.synchronous}")
    private boolean synchronous;

    private final SoundModulationFactory modulationFactory;
    private final SoundRepository soundRepository;

    public DefaultModulationManager(@Qualifier("standardSoundModulationFactory") SoundModulationFactory modulationFactory,
                                    @Qualifier("sqlSoundRepository") SoundRepository soundRepository) {
        this.modulationFactory = modulationFactory;
        this.soundRepository = soundRepository;
    }

    @Override
    public Optional<SoundModulation> addOrModifyModulator(SoundVariant soundVariant, ModulationId modulationId, ModulatorData data) {
        var modulators = soundVariant.getModulators();

        var modulatorOptional = modulators.stream().filter(modulator -> modulator.getId() == modulationId).findFirst();

        if (modulatorOptional.isPresent()) {
            var modulator = modulatorOptional.get();
            modulator.updateFromEndpoint(data);
            waitFuture(synchronous, soundRepository.updateModulator(modulator));
            return modulatorOptional;
        } else {
            var createdOptional = modulationFactory.deserializeFromEndpoint(soundVariant, modulationId, data);
            createdOptional.ifPresent(soundModulation -> {
                soundVariant.addModulator(soundModulation);
                waitFuture(synchronous, soundRepository.addModulator(soundModulation));
            });
            return createdOptional;
        }
    }

    @Override
    public void removeModulator(SoundVariant soundVariant, ModulationId id) {
        soundVariant.removeModulator(id)
                .ifPresent(modulator -> waitFuture(synchronous, soundRepository.removeModulator(modulator)));
    }
}
