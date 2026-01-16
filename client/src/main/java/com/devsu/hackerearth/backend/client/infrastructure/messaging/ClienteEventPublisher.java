package com.devsu.hackerearth.backend.client.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(ClienteEventPublisher.class);
    private static final String EXCHANGE = "cliente-events";

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ClienteEventPublisher(RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarClienteCreado(Long clienteId, String nombre) {

        String mensaje = String.format("{\"clienteId\":%d,\"nombre\":\"%s\",\"evento\":\"CLIENTE_CREADO\"}", clienteId,
                nombre);

        logger.info("Publicando evento CLIENTE_CREADO para cliente ID: {}, nombre: {}", clienteId, nombre);

        rabbitTemplate.convertAndSend(EXCHANGE, "cliente.creado", mensaje);

    }

    public void publicarClienteActualizado(Long clienteId, String nombre) {

        String mensaje = String.format("{\"clienteId\":%d,\"nombre\":\"%s\",\"evento\":\"CLIENTE_ACTUALIZADO\"}", clienteId,
                nombre);

        logger.info("Publicando evento CLIENTE_ACTUALIZADO para cliente ID: {}, nombre: {}", clienteId, nombre);

        rabbitTemplate.convertAndSend(EXCHANGE, "cliente.actualizado", mensaje);

    }

}
