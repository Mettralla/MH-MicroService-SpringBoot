package com.mindhub.productMicroservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange productExchange() {
        return new TopicExchange("product.request.exchange");
    }

    @Bean
    public Queue productRequestQueue() {
        return new Queue("product.request.queue");
    }

    @Bean
    public Binding productRequestBinding(Queue productRequestQueue, TopicExchange productExchange) {
        return BindingBuilder.bind(productRequestQueue).to(productExchange).with("product.request");
    }

    @Bean
    public Queue productStockUpdateQueue() {
        return new Queue("product.stock.update.queue");
    }

    @Bean
    public Binding productStockUpdateBinding(Queue productStockUpdateQueue, TopicExchange productExchange) {
        return BindingBuilder.bind(productStockUpdateQueue).to(productExchange).with("product.stock.routingkey");
    }

}