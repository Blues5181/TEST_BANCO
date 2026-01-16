package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.client.domain.model.Cliente;
import com.devsu.hackerearth.backend.client.domain.service.ClienteService;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class sampleTest {

    @Autowired
    private ClienteService clienteService;

    private Cliente clientePrueba;

    @BeforeEach
    void setUp() {

        clientePrueba = new Cliente();

        clientePrueba.setNombre("Juan Perez");
        clientePrueba.setGenero("M");
        clientePrueba.setIdentificacion("1234567890");
        clientePrueba.setDireccion("Ciudad");
        clientePrueba.setTelefono("78787878");
        clientePrueba.setContrasena("desa123$");
        clientePrueba.setEdad(28);
        clientePrueba.setEstado(true);
    }

    @Test
    void testCrearCliente() {
        Cliente clienteCreado = clienteService.crear(clientePrueba);

        assertNotNull(clienteCreado.getClienteId());
        assertEquals("Juan Perez", clienteCreado.getNombre());
        assertEquals("1234567890", clienteCreado.getIdentificacion());

        assertTrue(clienteCreado.getEstado());
    }

    @Test
    void testObtenerClientePorId() {

        Cliente clienteCreado = clienteService.crear(clientePrueba);

        Optional<Cliente> clienteEncontrado = clienteService.obtenerPorId(clienteCreado.getClienteId());

        assertTrue(clienteEncontrado.isPresent());
        assertEquals(clienteCreado.getClienteId(), clienteEncontrado.get().getClienteId());

    }

    @Test
    void testActualizarCliente() {

        Cliente clienteCreado = clienteService.crear(clientePrueba);

        clienteCreado.setNombre("Juan Carlos Lopez");
        clienteCreado.setContrasena("123A");

        Cliente clienteActualizado = clienteService.actualizar(clienteCreado.getClienteId(), clienteCreado);

        assertEquals("Juan Carlos Lopez", clienteActualizado.getNombre());
        assertNotNull(clienteActualizado.getContrasena());
        assertTrue(clienteActualizado.getContrasena().startsWith("$2a$") || clienteActualizado.getContrasena().startsWith("$2b$"));

    }

    @Test
    void testEliminarCliente() {

        Cliente clienteCreado = clienteService.crear(clientePrueba);
        Long clienteId = clienteCreado.getClienteId();

        clienteService.eliminar(clienteId);
        assertFalse(clienteService.obtenerPorId(clienteId).isPresent());
    }

    @Test
    void testClienteNoEncontrado() {

        assertThrows(RuntimeException.class, () -> {
            clienteService.obtenerPorId(999L).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        });
    }

}
