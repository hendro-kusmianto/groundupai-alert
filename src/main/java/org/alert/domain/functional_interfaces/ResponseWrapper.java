package org.alert.domain.functional_interfaces;

import org.alert.domain.model.ResponseEnvelope;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ResponseWrapper {
    public Mono<Object> wrap(ResponseEnvelope envelope);
}
