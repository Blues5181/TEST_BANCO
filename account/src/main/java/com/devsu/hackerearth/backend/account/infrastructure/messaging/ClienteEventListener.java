package com.devsu.hackerearth.backend.account.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.account.domain.model.ClienteResumen;
import com.devsu.hackerearth.backend.account.domain.repository.ClienteResumenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ClienteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ClienteEventListener.class);

    private final ClienteResumenRepository clienteResumenRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClienteEventListener(ClienteResumenRepository clienteResumenRepository, ObjectMapper objectMapper) {

        this.clienteResumenRepository = clienteResumenRepository;
        this.objectMapper = objectMapper;

    }

    @RabbitListener(queues = "cliente-events-queue")
    public void recibirEvnetoCliente(String mensaje) {

        logger.info("Evento recibido del microservicio cliente: {}", mensaje);

        try {

            JsonNode jsonNode = objectMapper.readTree(mensaje);

            Long clienteId = jsonNode.get("clienteId").asLong();
            String nombre = jsonNode.get("nombre").asText();
            ClienteResumen clienteResumen = clienteResumenRepository.findByClienteId(clienteId)
                    .orElse(new ClienteResumen());

            clienteResumen.setClienteId(clienteId);
            clienteResumen.setNombre(nombre);

            clienteResumenRepository.save(clienteResumen);
            logger.info("Cliente resumen guardado/actualizado: clienteId: {}, nombre: {}", clienteId, nombre);
        } catch (Exception e) {

            logger.error("Error al procesar evento de cliente: {}", e.getMessage(), e);
        }

    }

}
