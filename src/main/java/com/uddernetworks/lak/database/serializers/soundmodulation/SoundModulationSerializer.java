package com.uddernetworks.lak.database.serializers.soundmodulation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class SoundModulationSerializer extends JsonSerializer<SoundModulation> {

    @Override
    public void serialize(SoundModulation soundModulation, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", soundModulation.getId().getId());
        jsonGenerator.writeStringField("variant", soundModulation.getSoundVariant().getId().toString());
        jsonGenerator.writeObjectField("value", soundModulation.getSerializable());
        jsonGenerator.writeEndObject();
    }
}
