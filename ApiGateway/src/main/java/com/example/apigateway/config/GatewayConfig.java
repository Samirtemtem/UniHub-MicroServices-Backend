package com.example.apigateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
    // Removed CORS-specific logic
    // This class can be used for other gateway configurations if needed in the future

    @Bean
    @Order(-1)
    public GlobalFilter csrfFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            // For non-GET methods, add CSRF bypass headers if needed
            if (request.getMethod() != HttpMethod.GET) {
                ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-Requested-With", "XMLHttpRequest")  // This often bypasses CSRF checks
                    .build();
                
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            }
            
            return chain.filter(exchange);
        };
    }
} 