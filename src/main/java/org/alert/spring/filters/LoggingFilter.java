package io.octagram.spring.filters;

import io.octagram.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String requestUri = exchange.getRequest().getURI().getPath();

        return chain.filter(exchange).doFinally(signalType -> {
            double elapsed = Utils.elapsed(startTime);
            log.info(
                    "%s request to %s completed in %s s.".formatted(exchange.getRequest().getMethod(), requestUri, elapsed)
            );
        });
    }
}
