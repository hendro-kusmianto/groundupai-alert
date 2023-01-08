package io.alert.service;


import lombok.extern.slf4j.Slf4j;
import org.alert.domain.functional_interfaces.ResponseWrapper;
import org.alert.domain.model.ErrCode;
import org.alert.domain.model.ErrDetail;
import org.alert.domain.model.ResponseEnvelope;
import org.alert.utils.Utils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class WrapperService {

    public Mono<ResponseEntity<ResponseEnvelope>> response(ResponseWrapper f) {
        var envelope = new ResponseEnvelope();
        return f.wrap(envelope)
                .map(o -> ResponseEntity.ok().body(envelope))
                .doOnError(throwable -> {
                    Throwable exception = Exceptions.unwrap(throwable);
                    if (exception instanceof AppException) {
                        AppException appException = (AppException) exception;
                        envelope.getMeta().setCode(appException.getErr().getCode())
                                .setMessage(exception.getMessage());
                        envelope.setErrors(appException.getErrors());
                    }
                })
                .onErrorResume(WebExchangeBindException.class, e -> wrapBindingException(e))
                .onErrorResume(ConstraintViolationException.class, e -> wrapConstraintViolationException(e))
                .onErrorReturn(NonExistentEntityException.class, ResponseEntity.status(HttpStatus.NOT_FOUND).body(envelope))
                .onErrorReturn(ForbiddenOperationException.class, ResponseEntity.status(HttpStatus.FORBIDDEN).body(envelope))
                .onErrorReturn(InvalidFieldException.class, ResponseEntity.badRequest().body(envelope))
                .onErrorReturn(AppException.class, ResponseEntity.status(HttpStatus.CONFLICT).body(envelope))
                .onErrorReturn(Exception.class, ResponseEntity.internalServerError().body(new ResponseEnvelope(ErrCode.ERR_UNKNOWN)));

    }

    public Mono<ResponseEntity<ResponseEnvelope>> wrapBindingException(WebExchangeBindException e) {
        var resp = new ResponseEnvelope(ErrCode.ERR_BAD_REQUEST, "Field tidak valid");
        return Flux.fromIterable(e.getAllErrors())
                .map(objectErrors -> objectErrors)
                .cast(FieldError.class)
                .map(err -> new ErrDetail(Utils.camelToSnake(err.getField()), ErrCode.ERR_FIELD_INVALID, err.getDefaultMessage()))
                .collectList()
                .doOnSuccess(errDetails -> resp.setErrors(errDetails))
                .thenReturn(ResponseEntity.badRequest().body(resp));
    }

    public Mono<ResponseEntity<ResponseEnvelope>> wrapConstraintViolationException(ConstraintViolationException e) {
        var resp = new ResponseEnvelope(ErrCode.ERR_BAD_REQUEST, "Field tidak valid");

        return Flux.fromIterable(e.getConstraintViolations())
                .map(err -> {
                    String path = err.getPropertyPath().toString();
                    String field = path;
                    if (!path.isBlank()) {
                        String[] split = path.split("\\.");
                        field = split.length == 0 ? path : split[split.length - 1];
                    }
                    return new ErrDetail(Utils.camelToSnake(field), ErrCode.ERR_FIELD_INVALID, err.getMessage());
                })
                .collectList()
                .doOnSuccess(errDetails -> resp.setErrors(errDetails))
                .thenReturn(ResponseEntity.badRequest().body(resp));

    }

    public Mono<Void> response(ServerWebExchange exchange, HttpStatus httpStatus, ResponseEnvelope envelope) {

        return Mono.just(MarshallingUtils.marshalJsonSilently(envelope).getBytes(StandardCharsets.UTF_8))
                .flatMap(bytes -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    response.setStatusCode(httpStatus);
                    Mono<DataBuffer> dataBufferMono = Mono.just(response.bufferFactory().wrap(bytes));
                    return response.writeWith(dataBufferMono);
                })
                .then();

    }

    public Mono<Void> unAuthorized(ServerWebExchange exchange) {
        return response(exchange, HttpStatus.UNAUTHORIZED, new ResponseEnvelope(ErrCode.ERR_BAD_CREDENTIAL));

    }
}
