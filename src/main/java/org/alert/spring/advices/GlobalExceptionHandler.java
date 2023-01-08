package org.alert.spring.advices;

import lombok.extern.slf4j.Slf4j;
import org.alert.domain.model.ResponseEnvelope;
import org.alert.services.WrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private WrapperService wrapper;

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ResponseEnvelope>> handleException(WebExchangeBindException e) {
        return wrapper.wrapBindingException(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<ResponseEnvelope>> handleException(ConstraintViolationException e) {
        return wrapper.wrapConstraintViolationException(e);
    }

}
