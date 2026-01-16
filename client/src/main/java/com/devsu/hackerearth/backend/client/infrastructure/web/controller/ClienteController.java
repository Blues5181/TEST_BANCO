package com.devsu.hackerearth.backend.client.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.client.domain.model.Cliente;
import com.devsu.hackerearth.backend.client.domain.service.ClienteService;
import com.devsu.hackerearth.backend.client.infrastructure.messaging.ClienteEventPublisher;
import com.devsu.hackerearth.backend.client.infrastructure.web.dto.ClienteRequest;
import com.devsu.hackerearth.backend.client.infrastructure.web.dto.ClienteResponse;
import com.devsu.hackerearth.backend.client.infrastructure.web.mapper.ClienteMapper;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteEventPublisher eventPublisher;

    @Autowired
    public ClienteController(ClienteService clienteService, ClienteEventPublisher eventPublisher) {
        this.clienteService = clienteService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@RequestBody ClienteRequest request) {

        Cliente cliente = ClienteMapper.toEntity(request);
        Cliente clienteCreado = clienteService.crear(cliente);

        eventPublisher.publicarClienteCreado(clienteCreado.getClienteId(), clienteCreado.getNombre());

        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteMapper.toResponse(clienteCreado));

    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> obtenerTodos() {

        List<ClienteResponse> clientes = clienteService.obtenerTodos().stream()
                .map(ClienteMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(cliente -> ResponseEntity.ok(ClienteMapper.toResponse(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<List<ClienteResponse>> obtenerPorNombre(@PathVariable String nombre) {
        List<ClienteResponse> clientes = clienteService.obtenerPorNombre(nombre).stream()
                .map(ClienteMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizar(@PathVariable Long id,
            @RequestBody ClienteRequest request) {
        Cliente cliente = ClienteMapper.toEntity(request);
        Cliente clienteActualizado = clienteService.actualizar(id, cliente);
        eventPublisher.publicarClienteActualizado(clienteActualizado.getClienteId(),
                clienteActualizado.getNombre());
        return ResponseEntity.ok(ClienteMapper.toResponse(clienteActualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizarParcial(@PathVariable Long id,
            @RequestBody ClienteRequest request) {
        Cliente clienteExistente = clienteService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (request.getPersona() != null) {
            if (request.getPersona().getNombre() != null) {
                clienteExistente.setNombre(request.getPersona().getNombre());
            }
            if (request.getPersona().getGenero() != null) {
                clienteExistente.setGenero(request.getPersona().getGenero());
            }
            if (request.getPersona().getEdad() != null) {
                clienteExistente.setEdad(request.getPersona().getEdad());
            }
            if (request.getPersona().getDireccion() != null) {
                clienteExistente.setDireccion(request.getPersona().getDireccion());
            }
            if (request.getPersona().getTelefono() != null) {
                clienteExistente.setTelefono(request.getPersona().getTelefono());
            }
        }

        if (request.getContrasena() != null) {
            clienteExistente.setContrasena(request.getContrasena());
        }
        if (request.getEstado() != null) {
            clienteExistente.setEstado(request.getEstado());
        }

        Cliente clienteActualizado = clienteService.actualizar(id, clienteExistente);
        return ResponseEntity.ok(ClienteMapper.toResponse(clienteActualizado));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}