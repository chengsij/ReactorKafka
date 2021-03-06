package com.chengsij.webflux;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaService<T> {
  private static final Logger log = LoggerFactory.getLogger(KafkaService.class.getName());

  public final KafkaSender<Integer, String> sender;

  public KafkaService(@Value("${kafka.bootStrapServer}") String bootStrapServer) {
    Map<String, Object> properties = new HashMap<>();
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
    properties.put(ProducerConfig.CLIENT_ID_CONFIG, "my-producer");
    properties.put(ProducerConfig.ACKS_CONFIG, "all");
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    SenderOptions<Integer, String> configuration = SenderOptions.create(properties);

    sender = KafkaSender.create(configuration);
  }

  public Flux<SenderResult<T>> sendMessages(
      Publisher<? extends SenderRecord<Integer, String, T>> outboundRecords) {
    return sender.send(outboundRecords).doOnError(e -> log.error("Send failed", e));
  }

  public void close() {
    sender.close();
  }
}
