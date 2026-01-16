package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.domain.exception.SaldoNoDisponibleException;
import com.devsu.hackerearth.backend.account.domain.model.Cuenta;
import com.devsu.hackerearth.backend.account.domain.model.Transaccion;
import com.devsu.hackerearth.backend.account.domain.repository.CuentaRepository;
import com.devsu.hackerearth.backend.account.domain.repository.TransaccionRepository;
import com.devsu.hackerearth.backend.account.domain.service.CuentaService;
import com.devsu.hackerearth.backend.account.domain.service.TransaccionService;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class sampleTest {

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private TransaccionService transaccionService;

	@Autowired
	private CuentaRepository cuentaRepository;

	@Autowired
	private TransaccionRepository transaccionRepository;

	private Cuenta cuentaPrueba;

	@BeforeEach
	void setUp() {
		cuentaPrueba = new Cuenta();

		cuentaPrueba.setNumero("123434343");
		cuentaPrueba.setTipo("AHORROS");
		cuentaPrueba.setSaldoInicial(new BigDecimal(1000.00));
		cuentaPrueba.setClienteId(1L);
		cuentaPrueba.setEstado(true);

	}

	@Test
	void testCrearCuenta() {
		Cuenta cuentaCreada = cuentaService.crear(cuentaPrueba);

		assertNotNull(cuentaCreada.getCuentaId());
		assertEquals("123434343", cuentaCreada);
		assertEquals(new BigDecimal(1000.00), cuentaCreada.getSaldoDisponible());
	}

	@Test
	void testCrearTransaccionDeposito() {
		Cuenta cuentaCreada = cuentaService.crear(cuentaPrueba);
		Transaccion transaccion = new Transaccion();

		transaccion.setTipo("DEPOSITO");
		transaccion.setValor(new BigDecimal(500.00));
		transaccion.setCuenta(cuentaCreada);

		Transaccion transaccionCreada = transaccionService.crear(transaccion);

		assertNotNull(transaccionCreada.getTransaccionId());
		assertEquals(new BigDecimal(1500.00), transaccionCreada.getSaldo());

		Cuenta cuentaActualizada = cuentaService.obtenerPorId(cuentaCreada.getCuentaId()).orElseThrow();
		assertEquals(new BigDecimal(1500.00), cuentaActualizada.getSaldoDisponible());

	}

	@Test
	void testCrearTransaccionRetiro() {
		Cuenta cuentaCreada = cuentaService.crear(cuentaPrueba);
		Transaccion transaccion = new Transaccion();

		transaccion.setTipo("RETIRO");
		transaccion.setValor(new BigDecimal(-300.00));
		transaccion.setCuenta(cuentaCreada);

		Transaccion transaccionCreada = transaccionService.crear(transaccion);

		assertNotNull(transaccionCreada.getTransaccionId());
		assertEquals(new BigDecimal(700.00), transaccionCreada.getSaldo());

		Cuenta cuentaActualizada = cuentaService.obtenerPorId(cuentaCreada.getCuentaId()).orElseThrow();
		assertEquals(new BigDecimal(700.00), cuentaActualizada.getSaldoDisponible());

	}

	@Test
	void testCrearTransaccionRetiroPositivo() {
		Cuenta cuentaCreada = cuentaService.crear(cuentaPrueba);
		Transaccion transaccion = new Transaccion();

		transaccion.setTipo("RETIRO");
		transaccion.setValor(new BigDecimal(300.00));
		transaccion.setCuenta(cuentaCreada);

		Transaccion transaccionCreada = transaccionService.crear(transaccion);

		assertNotNull(transaccionCreada.getTransaccionId());
		assertEquals(new BigDecimal(700.00), transaccionCreada.getSaldo());

		Cuenta cuentaActualizada = cuentaService.obtenerPorId(cuentaCreada.getCuentaId()).orElseThrow();
		assertEquals(new BigDecimal(700.00), cuentaActualizada.getSaldoDisponible());

	}

	@Test
	void testValidarSaldoNoDisponible() {

		Cuenta cuentaCreada = cuentaService.crear(cuentaPrueba);
		Transaccion transaccion = new Transaccion();

		transaccion.setTipo("RETIRO");
		transaccion.setValor(new BigDecimal(-50000.00));
		transaccion.setCuenta(cuentaCreada);

		assertThrows(SaldoNoDisponibleException.class, () -> {
			transaccionService.crear(transaccion);

		}, "Saldo no Disponible");

	}

	@Test
	void testIntegracionCuentaYTransacciones() {
		Cuenta cuentaCreada = cuentaService.crear(cuentaPrueba);
		Transaccion deposito = new Transaccion();

		deposito.setTipo("DEPOSITO");
		deposito.setValor(new BigDecimal(200.00));
		deposito.setCuenta(cuentaCreada);

		Transaccion retiro = new Transaccion();

		retiro.setTipo("RETIRO");
		retiro.setValor(new BigDecimal(-100.00));
		retiro.setCuenta(cuentaCreada);
		transaccionService.crear(retiro);
		Cuenta cuentaActualizada = cuentaService.obtenerPorId(cuentaCreada.getCuentaId()).get();

		assertEquals(new BigDecimal(1100), cuentaActualizada.getSaldoDisponible());
		var transaccion = transaccionService.obtneerPorcuentaId(cuentaCreada.getCuentaId());
		assertEquals(2, transaccion.size());
	}
}
