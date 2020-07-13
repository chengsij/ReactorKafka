package com.chengsij.webflux.handler;

import com.chengsij.webflux.KafkaService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EventHandler {
  private static final Logger log = LoggerFactory.getLogger(EventHandler.class.getName());

  private static final String BOOTSTRAP_SERVERS = "localhost:9092";
  private static final String TOPIC = "my-topic";
  private final SimpleDateFormat dateFormat;
  KafkaService<Integer> service;

  public EventHandler() {
    dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS z ");
    service = new KafkaService<>(BOOTSTRAP_SERVERS);
  }

  public Mono<ServerResponse> publish(ServerRequest request) {
    return request
        .bodyToMono(String.class)
        .flatMap(
            m -> {
              Mono<SenderRecord<Integer, String, Integer>> record = createRecord(TOPIC, 0, m, 1);
              return Mono.from(service.sendMessages(record));
            })
        .flatMap(
            p ->
                ServerResponse.created(URI.create("/event/"))
                    .bodyValue(
                        String.format("Published message at %s", dateFormat.format(new Date()))));
  }

  public Mono<SenderResult<Integer>> publish(String message) {
    Mono<SenderRecord<Integer, String, Integer>> record = createRecord(TOPIC, 0, message, 1);
    return Mono.from(service.sendMessages(record));
  }

  private Mono<SenderRecord<Integer, String, Integer>> createRecord(
      String topic, Integer key, String value, Integer correlationMetadata) {
    return Mono.just(SenderRecord.create(new ProducerRecord<>(topic, key, value), 1));
  }
}
