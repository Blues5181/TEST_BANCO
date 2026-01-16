package com.devsu.hackerearth.backend.account.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE = "cliente-events";
    private static final String QUEUE = "cliente-events-queue";
    private static final String ROUTING_KEY_CREADO = "cliente.creado";
    private static final String ROUTING_KEY_ACTUALIZADO = "cliente.actualizado";

    @Bean
    public TopicExchange clienteEventsExchange() {

        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue clienteEventsQueu() {

        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding bindingClienteCreado() {

        return BindingBuilder.bind(clienteEventsQueu())
                .to(clienteEventsExchange())
                .with(ROUTING_KEY_CREADO);
    }

    @Bean
    public Binding bindingClienteActualizado() {

        return BindingBuilder.bind(clienteEventsQueu())
                .to(clienteEventsExchange())
                .with(ROUTING_KEY_ACTUALIZADO);
    }
}
