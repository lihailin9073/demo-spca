package com.wzliulan.spca.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        GatewayFilter gatewayFilter = new GatewayFilter(){
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                log.info("已进入PreLogGatewayFilterFactory过滤器...{},{}", config.getName(), config.getValue());
                // TODO 这里可以修改 request
                ServerHttpRequest request = exchange.getRequest().mutate().build();
                // TODO 这里可以修改 exchange
                ServerWebExchange webExchange = exchange.mutate().request(request).build();
                return chain.filter(webExchange);
            }
        };
        return gatewayFilter;
    }
}
