package com.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class TestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getQueryParams().get("Test"));
        System.out.println(request.getQueryParams());

        List<String> value = request.getQueryParams().get("test");
        if (value != null && value.contains("1")) return chain.filter(exchange);
        return exchange.getResponse().setComplete();

    }

    @Override
    public int getOrder() {
        return 0;
    }

}
