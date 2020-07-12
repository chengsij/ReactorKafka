package com.chengsij.webflux;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ServerSenderResult<T> extends Response implements ServerResponse {
  private final HttpStatus statusCode;
  private final int rawStatusCode;
  private final HttpHeaders headers;

  public ServerSenderResult(
      RecordMetadata metadata,
      Exception exception,
      T correlationMetadata,
      HttpStatus statusCode,
      int rawStatusCode,
      HttpHeaders headers) {
    super(metadata, exception, correlationMetadata);
    this.statusCode = statusCode;
    this.rawStatusCode = rawStatusCode;
    this.headers = headers;
  }

  @Override
  public HttpStatus statusCode() {
    return this.statusCode;
  }

  @Override
  public int rawStatusCode() {
    return this.rawStatusCode;
  }

  @Override
  public HttpHeaders headers() {
    return this.headers;
  }

  @Override
  public MultiValueMap<String, ResponseCookie> cookies() {
    return null;
  }

  @Override
  public Mono<Void> writeTo(ServerWebExchange exchange, Context context) {
    return null;
  }
}
