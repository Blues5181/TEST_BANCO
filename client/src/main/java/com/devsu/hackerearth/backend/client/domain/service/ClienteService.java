package com.devsu.hackerearth.backend.client.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.client.domain.model.Cliente;
import com.devsu.hackerearth.backend.client.domain.repository.ClienteRepository;
import com.devsu.hackerearth.backend.client.domain.repository.PersonaRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PersonaRepository personaRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(readOnly = false)
    public Cliente crear(Cliente cliente) {

        if (personaRepository.findByIdentificacion(cliente.getIdentificacion()).isPresent()) {

            throw new RuntimeException("Ya existe una persona con esta identificacion");
        }

        if (cliente.getContrasena() != null && !cliente.getContrasena().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(cliente.getContrasena());
            cliente.setContrasena(hashedPassword);
        }
        return clienteRepository.save(cliente);

    }

    @Transactional(readOnly = true)
    public List<Cliente> obtenerTodos() {

        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cliente> obtenerPorNombre(String nombre) {

        return clienteRepository.findByNombre(nombre)
                .map(List::of)
                .orElse(List.of());

    }

    @Transactional(readOnly = false)
    public Cliente actualizar(Long id, Cliente clienteActualizado) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setGenero(clienteActualizado.getGenero());
        cliente.setEdad(clienteActualizado.getEdad());
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setTelefono(clienteActualizado.getTelefono());

        if (clienteActualizado.getContrasena() != null && !clienteActualizado.getContrasena().isEmpty()) {

            String nuevaContrasena = clienteActualizado.getContrasena();
            if (!nuevaContrasena.startsWith("$2a$") && !nuevaContrasena.startsWith("$2b$")) {
                nuevaContrasena = passwordEncoder.encode(nuevaContrasena);
            }
            cliente.setContrasena(nuevaContrasena);
        }

        cliente.setEstado(clienteActualizado.getEstado());

        return clienteRepository.save(cliente);

    }

    @Transactional(readOnly = false)
    public void eliminar(Long id) {

        if (!clienteRepository.existsById(id)) {

            throw new RuntimeException("Cliente no encontrado");
        }

        clienteRepository.deleteById(id);
    }

}
