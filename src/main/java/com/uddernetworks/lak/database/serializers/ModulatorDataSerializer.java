package com.uddernetworks.lak.database.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.uddernetworks.lak.sounds.modulation.ModulatorData;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Map;

@JsonComponent
public class ModulatorDataSerializer {
    public static class ModulatorDataJsonSerializer extends JsonSerializer<ModulatorData> {

        @Override
        public void serialize(ModulatorData data, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeObject(data.getData());
        }
    }

    public static class ModulatorDataJsonDeserializer extends JsonDeserializer<ModulatorData> {

        @Override
        public ModulatorData deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            return new ModulatorData((Map<String, Object>) jsonParser.getCodec().readValue(jsonParser, Map.class));
        }
    }
}
