package com.processing.serdes;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.processing.models.SimpleMessage;

public class SimpleMessageSerdes
        implements Serializer<SimpleMessage>, Deserializer<SimpleMessage>, Serde<SimpleMessage> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {

    }

    @SuppressWarnings("uncheked")
    @Override
    public SimpleMessage deserialize(final String topic, final byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return (SimpleMessage) OBJECT_MAPPER.readValue(data, SimpleMessage.class);
        } catch (final IOException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public byte[] serialize(final String topic, final SimpleMessage data) {
        if (data == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsBytes(data);
        } catch (final IOException e) {
            throw new SerializationException("Error serializing JSON message");
        }
    }

    @Override
    public void close() {
    }

    @Override
    public Serializer<SimpleMessage> serializer() {
        return this;
    }

    @Override
    public Deserializer<SimpleMessage> deserializer() {
        return this;
    }

}