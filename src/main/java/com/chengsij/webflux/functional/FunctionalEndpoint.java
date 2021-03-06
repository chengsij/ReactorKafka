package com.chengsij.webflux.functional;

import com.chengsij.webflux.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/** this class defines the routes of the application. */
@Configuration
@Profile("default")
public class FunctionalEndpoint {

  @Value("kafka:bootStrapServer")
  private static String BOOTSTRAP_SERVERS;

  private static RequestPredicate i(RequestPredicate target) {
    return new CaseInsensitiveRequestPredicate(target);
  }

  @Bean
  RouterFunction<ServerResponse> routes(EventHandler handler) {
    return route(i(POST("/event")), handler::publish);
  }
}
