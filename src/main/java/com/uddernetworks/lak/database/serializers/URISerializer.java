package com.uddernetworks.lak.database.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.net.URI;

@JsonComponent
public class URISerializer {

    public static class URIJsonSerializer extends JsonSerializer<URI> {

        @Override
        public void serialize(URI value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeString(value.toString());
        }
    }

    public static class URIJsonDeserializer extends JsonDeserializer<URI> {

        @Override
        public URI deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            var treeNode = jsonParser.getCodec().readValue(jsonParser, String.class);

            try {
                return URI.create(treeNode);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

}
