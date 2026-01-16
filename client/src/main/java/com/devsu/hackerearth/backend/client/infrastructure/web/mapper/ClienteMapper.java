package com.devsu.hackerearth.backend.client.infrastructure.web.mapper;

import com.devsu.hackerearth.backend.client.domain.model.Cliente;
import com.devsu.hackerearth.backend.client.infrastructure.web.dto.ClienteRequest;
import com.devsu.hackerearth.backend.client.infrastructure.web.dto.ClienteResponse;
import com.devsu.hackerearth.backend.client.infrastructure.web.dto.PersonaResponse;

public class ClienteMapper {
    public static Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();

        cliente.setNombre(request.getPersona().getNombre());
        cliente.setGenero(request.getPersona().getGenero());
        cliente.setEdad(request.getPersona().getEdad());
        cliente.setIdentificacion(request.getPersona().getIdentificacion());
        cliente.setDireccion(request.getPersona().getDireccion());
        cliente.setTelefono(request.getPersona().getTelefono());

        cliente.setContrasena(request.getContrasena());
        cliente.setEstado(request.getEstado() != null ? request.getEstado() : true);

        return cliente;
    }

    public static ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        PersonaResponse personaResponse = new PersonaResponse();

        personaResponse.setId(cliente.getId());
        personaResponse.setNombre(cliente.getNombre());
        personaResponse.setGenero(cliente.getGenero());
        personaResponse.setEdad(cliente.getEdad());
        personaResponse.setIdentificacion(cliente.getIdentificacion());
        personaResponse.setDireccion(cliente.getDireccion());
        personaResponse.setTelefono(cliente.getTelefono());

        response.setClienteId(cliente.getClienteId());
        response.setPersona(personaResponse);
        response.setEstado(cliente.getEstado());

        return response;
    }

}
