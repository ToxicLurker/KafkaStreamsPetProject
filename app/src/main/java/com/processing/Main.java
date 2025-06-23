package com.processing;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import com.processing.models.SimpleMessage;
import com.processing.serdes.SimpleMessageSerdes;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testTopic");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SimpleMessageSerdes.class);

        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, SimpleMessage> source = builder.stream("testTopic",
                Consumed.with(Serdes.String(), new SimpleMessageSerdes()));
        source.foreach((k, v) -> {
            System.out.println("Got value " + v.getContent() + "; Got key:" + k);
        });
        KTable<String, Integer> test_ktable = source.map((k, v) -> new KeyValue<>(v.message, v.message4))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Integer())).reduce(Integer::sum);
        test_ktable.toStream().peek((k, v) -> {
            System.out.println("New key: " + v + "; Aggregated value:" + k);
        }).mapValues(v -> String.format("%d common sum", v)).to("testTopic3",
                Produced.with(Serdes.String(), Serdes.String()));
        source.to("testTopic2");
        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);

        System.out.println(topology.describe());
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}