package com.devsu.hackerearth.backend.client.infrastructure.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Exchange;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Exchange clienteExchange() {

        return new TopicExchange("cliente-events", true, false);
    }
}
