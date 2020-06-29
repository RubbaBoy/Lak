package com.uddernetworks.lak.database.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonComponent;

import java.awt.Color;
import java.io.IOException;

import static com.uddernetworks.lak.Utility.colorFromHex;
import static com.uddernetworks.lak.Utility.hexFromColor;

@JsonComponent
public class ColorSerializer {

    public static class ColorJsonSerializer extends JsonSerializer<Color> {

        private static final Logger LOGGER = LoggerFactory.getLogger(ColorSerializer.class);

        @Override
        public void serialize(Color color, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeString(hexFromColor(color));
        }
    }

    public static class ColorJsonDeserializer extends JsonDeserializer<Color> {

        private static final Logger LOGGER = LoggerFactory.getLogger(ColorJsonDeserializer.class);

        @Override
        public Color deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            var hex = jsonParser.getCodec().readValue(jsonParser, String.class);
            LOGGER.debug("decoding hex = {}", hex);

            try {
                var col = colorFromHex(hex);
                LOGGER.debug("col = {}", col);
                return col;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
