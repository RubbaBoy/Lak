package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("defaultModulationManager")
public class DefaultModulationManager implements ModulationManager {

    private final SoundModulationFactory modulationFactory;

    public DefaultModulationManager(@Qualifier("standardSoundModulationFactory") SoundModulationFactory modulationFactory) {
        this.modulationFactory = modulationFactory;
    }

    @Override
    public Optional<SoundModulation> addOrModifyModulator(SoundVariant soundVariant, ModulationId modulationId, ModulatorData data) {
        var modulators = soundVariant.getModulators();

        var modulatorOptional = modulators.stream().filter(modulator -> modulator.getId() == modulationId).findFirst();

        if (modulatorOptional.isPresent()) {
            var modulator = modulatorOptional.get();
            modulator.updateFromEndpoint(data);
            return modulatorOptional;
        } else {
            var createdOptional = modulationFactory.deserializeFromEndpoint(soundVariant, modulationId, data);
            createdOptional.ifPresent(soundVariant::addModulator);
            return createdOptional;
        }
    }

    @Override
    public void removeModulator(SoundVariant soundVariant, ModulationId id) {
        soundVariant.getModulators().removeIf(modulator -> modulator.getId() == id);
    }
}
