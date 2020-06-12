package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;
import org.springframework.stereotype.Component;

@Component("defaultModulationManager")
public class DefaultModulationManager implements ModulationManager {

    @Override
    public SoundModulation addOrModifyModulator(SoundVariant soundVariant, ModulationId modulationId, ModulatorData data) {
        return null;
    }

    @Override
    public void removeModulator(SoundVariant soundVariant, ModulationId id) {

    }
}
