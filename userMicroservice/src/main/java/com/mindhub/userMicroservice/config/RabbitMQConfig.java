package com.mindhub.userMicroservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("product.exchange");
    }

    @Bean
    public Queue queue() {
        return new Queue("product.queue");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("product.routingkey");
    }

    // EMAIL

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange("email.exchange");
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email.queue");
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("email.routingkey");
    }

    // USER
    @Bean
    public Queue userQueue() {
        return new Queue("user.queue");
    }

    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(userQueue).to(emailExchange).with("user.routingkey");
    }
}