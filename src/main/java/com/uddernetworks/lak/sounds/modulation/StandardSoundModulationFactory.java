package com.uddernetworks.lak.sounds.modulation;

import com.uddernetworks.lak.sounds.SoundVariant;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Optional;

@Component("standardSoundModulationFactory")
public class StandardSoundModulationFactory implements SoundModulationFactory {

    @Override
    public Optional<SoundModulation> deserialize(SoundVariant soundVariant, byte[] bytes) {
        var buffer = ByteBuffer.wrap(bytes);
        return ModulationId.getFromId(buffer.get()).map(modulationId -> {
            switch (modulationId) {
                case VOLUME:
                    return VolumeModulation.deserialize(soundVariant, buffer);
                case PITCH:
                    return PitchModulation.deserialize(soundVariant, buffer);
            }
            return null;
        });
    }
}
