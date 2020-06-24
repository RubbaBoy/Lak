package com.uddernetworks.lak.database.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.uddernetworks.lak.keys.KeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.awt.Color;
import java.io.IOException;

import static com.uddernetworks.lak.Utility.colorFromHex;
import static com.uddernetworks.lak.Utility.hexFromColor;

@JsonComponent
public class KeyEnumSerializer {

    public static class KeyEnumJsonSerializer extends JsonSerializer<KeyEnum> {

        private static final Logger LOGGER = LoggerFactory.getLogger(KeyEnumSerializer.class);

        @Override
        public void serialize(KeyEnum keyEnum, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("linuxCode", keyEnum.getLinuxCode());
            jsonGenerator.writeBooleanField("shift", keyEnum.isShift());
            jsonGenerator.writeEndObject();
        }
    }

    public static class KeyEnumJsonDeserializer extends JsonDeserializer<KeyEnum> {

        @Override
        public KeyEnum deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            var treeNode = jsonParser.getCodec().readTree(jsonParser);
            var linuxCode = (IntNode) treeNode.get("linuxCode");
            var shift = (BooleanNode) treeNode.get("shift");

            return KeyEnum.fromLinuxCode(linuxCode.intValue(), shift.asBoolean()).orElse(null);
        }
    }

}
