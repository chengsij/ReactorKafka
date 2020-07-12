package com.chengsij.webflux.classic;

import com.chengsij.webflux.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@RestController
@RequestMapping("/classic")
public class ClassicEndpoint {
  @Autowired EventHandler handler;

  @PostMapping(value = "/event")
  public Mono<SenderResult<Integer>> publishEvent(@RequestBody String message) {
    return handler.publish(message);
  }
}
