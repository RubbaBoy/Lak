package com.uddernetworks.lak.database.serializers.soundmodulation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.uddernetworks.lak.sounds.SoundManager;
import com.uddernetworks.lak.sounds.modulation.ModulationId;
import com.uddernetworks.lak.sounds.modulation.ModulatorData;
import com.uddernetworks.lak.sounds.modulation.SoundModulation;
import com.uddernetworks.lak.sounds.modulation.SoundModulationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.UUID;

@JsonComponent
public class SoundModulationDeserializer extends JsonDeserializer<SoundModulation> {

    @Autowired
    @Qualifier("variableSoundManager")
    private SoundManager soundManager;

    @Autowired
    @Qualifier("standardSoundModulationFactory")
    private SoundModulationFactory modulationFactory;

    @Autowired
    private ObjectMapper objectMapper;

    public SoundModulationDeserializer() {
    }

    @Override
    public SoundModulation deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        var treeNode = jsonParser.getCodec().readTree(jsonParser);
        var id = (IntNode) treeNode.get("id");
        var variantID = UUID.fromString(((TextNode) treeNode.get("variant")).asText());
        var value = treeNode.get("value");

        var modulatorData = objectMapper.treeToValue(value, ModulatorData.class);

        var variantOptional = soundManager.getVariant(variantID);
        if (variantOptional.isEmpty()) {
            return null;
        }

        var variant = variantOptional.get();
        var modulationIdOptional = ModulationId.getFromId((byte) id.asInt());
        if (modulationIdOptional.isEmpty()) {
            return null;
        }

        return modulationFactory.deserializeFromEndpoint(variant, modulationIdOptional.get(), modulatorData).orElse(null);
    }

}
