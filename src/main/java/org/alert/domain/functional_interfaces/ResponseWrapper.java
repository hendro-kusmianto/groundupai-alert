package io.octagram.domain.interfaces;

import io.octagram.domain.dto.ResponseEnvelope;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ResponseWrapper {
    public Mono<Object> wrap(ResponseEnvelope envelope);
}
