package org.alert.domain.functional_interfaces;

import reactor.core.publisher.Mono;

public class ResponseWrapperImpl {
    public Mono<Object> wrapper(ResponseWrapper f) {

        return Mono.just(true);
    }
}
