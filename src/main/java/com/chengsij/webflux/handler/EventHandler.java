package com.chengsij.webflux.handler;

import com.chengsij.webflux.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.text.SimpleDateFormat;

@Component
public class EventHandler {
  private static final Logger log = LoggerFactory.getLogger(EventHandler.class.getName());

  private static final String BOOTSTRAP_SERVERS = "localhost:9092";
  private static final String TOPIC = "my-topic";
  private final SimpleDateFormat dateFormat;
  KafkaService service;

  public EventHandler() {
    dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS z ");
    service = new KafkaService(BOOTSTRAP_SERVERS);
  }

  public Mono<ServerResponse> publish(ServerRequest request) {
//    return request
//        .bodyToMono(String.class)
//        .flatMap(
//            m -> {
//              service
//                  .sendMessages(TOPIC, m, null);
//                  .subscribe(
//                      r -> {
//                        RecordMetadata metadata = r.recordMetadata();
//                        System.out.printf(
//                            "Message %d sent successfully, topic-partition=%s-%d offset=%d timestamp=%s\n",
//                            r.correlationMetadata(),
//                            metadata.topic(),
//                            metadata.partition(),
//                            metadata.offset(),
//                            dateFormat.format(new Date(metadata.timestamp())));
//                      });
//            })
//        .flatMap(p -> ServerResponse.created(URI.create("/event/")).build())
//            .;
      return null;
  }

  public Mono<SenderResult<Integer>> publish(String message) {
        return Mono.from(service.sendMessages(TOPIC, message, 1));

    }
}
