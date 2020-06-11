package com.uddernetworks.lak.database;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

@JsonComponent
public class URICombinedSerializer {

    public static class URISerializer extends JsonSerializer<URI> {

        @Override
        public void serialize(URI value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeString(value.toString());
        }
    }

    public static class URIDeserializer extends JsonDeserializer<URI> {

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
