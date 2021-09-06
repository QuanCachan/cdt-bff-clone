package com.cacf.cdt.bffclone.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.sleuth.autoconfig.SleuthBaggageProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Component
@Slf4j
public class RequestLoggingFilter implements WebFilter {

    private final SleuthBaggageProperties sleuthBaggageProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        val request = exchange.getRequest();
        val response = exchange.getResponse();
        sleuthBaggageProperties.getCorrelationFields()
                .forEach(field -> {
                    val fieldValue = Optional.ofNullable(MDC.get(field)).filter(Predicate.not(StringUtils::isBlank));
                    if (fieldValue.isEmpty()) {
                        val uuid = UUID.randomUUID().toString();
                        log.info("Blank correlation id field ({}={}), initialise it to {}", field, MDC.get(field), uuid);
                        MDC.put(field, uuid);
                    }
                    response.getHeaders().putIfAbsent(field, List.of(MDC.get(field)));
                });
        log.info("URI : {}, Headers {}", request.getURI(), request.getHeaders());
        return chain.filter(exchange);
    }
}
