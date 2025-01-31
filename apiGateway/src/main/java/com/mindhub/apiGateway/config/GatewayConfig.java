package com.mindhub.apiGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator customRouter(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("userMicroservice", r -> r.path("/api/users/**")
                        .filters(f -> f
                                .filter(jwtAuthenticationFilter)
                                .circuitBreaker(c -> c.setName("userCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/users")))
                        .uri("http://localhost:8081"))
                .route("userMicroservice", r -> r.path("/api/auth/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("userCircuitBreaker")
                                .setFallbackUri("forward:/fallback/users")))
                        .uri("http://localhost:8081"))
                .route("productMicroservice", r -> r.path("/api/products/**")
                        .filters(f -> f
                                .filter(jwtAuthenticationFilter)
                                .circuitBreaker(c -> c.setName("productCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/products")))
                        .uri("http://localhost:8082"))
                .route("orderMicroservice", r -> r.path("/api/orders/**")
                        .filters(f -> f
                                .filter(jwtAuthenticationFilter)
                                .circuitBreaker(c -> c.setName("orderCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/orders")))
                        .uri("http://localhost:8083"))
                .build();
    }
}
