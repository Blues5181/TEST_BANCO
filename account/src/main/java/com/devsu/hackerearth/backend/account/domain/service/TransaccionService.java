package com.devsu.hackerearth.backend.account.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.domain.exception.SaldoNoDisponibleException;
import com.devsu.hackerearth.backend.account.domain.model.Cuenta;
import com.devsu.hackerearth.backend.account.domain.model.Transaccion;
import com.devsu.hackerearth.backend.account.domain.repository.CuentaRepository;
import com.devsu.hackerearth.backend.account.domain.repository.TransaccionRepository;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public TransaccionService(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {

        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional(readOnly = false)
    public Transaccion crear(Transaccion transaccion) {
        Cuenta cuenta = cuentaRepository.findById(transaccion.getCuenta().getCuentaId())
                .orElseThrow(
                        () -> new RuntimeException("Cuenta no encontrada"));

        if (!cuenta.getEstado()) {

            throw new RuntimeException("La cuenta esta inactiva");
        }

        BigDecimal valorNormalizado = normalizarValor(transaccion.getTipo(), transaccion.getValor());
        transaccion.setValor(valorNormalizado);

        BigDecimal nuevoSaldo = cuenta.getSaldoDisponible().add(valorNormalizado);

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {

            throw new SaldoNoDisponibleException("El saldo no puede ser cero");

        }

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {

            throw new SaldoNoDisponibleException("Saldo no disponible");
        }

        cuenta.setSaldoDisponible(nuevoSaldo);
        transaccion.setSaldo(nuevoSaldo);
        transaccion.setCuenta(cuenta);

        cuentaRepository.save(cuenta);
        return transaccionRepository.save(transaccion);

    }

    private BigDecimal normalizarValor(String tipo, BigDecimal valor) {

        if (valor == null) {

            throw new RuntimeException("El valor de la transaccion no puede ser nulo");

        }

        if (tipo == null || tipo.trim().isEmpty()) {

            throw new RuntimeException("El tipo de transaccion no puede ser nulo o vacio");
        }

        String tipoNormalizado = tipo.trim().toUpperCase();

        BigDecimal valorAbsoluto = valor.abs();

        if ("RETIRO".equals(tipoNormalizado)) {

            return valorAbsoluto.negate();
        } else if ("DEPOSITO".equals(tipoNormalizado)) {

            return valorAbsoluto;
        } else {

            throw new RuntimeException("Tipo de transaccion no valido: " + tipo + ". Debe ser RETIRO o DEPOSITO");
        }
    }

    @Transactional(readOnly = true)
    public List<Transaccion> obtenerTodas() {

        return transaccionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Transaccion> obtenerPorId(Long id) {

        return transaccionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Transaccion> obtneerPorcuentaId(Long cuentaId) {

        return transaccionRepository.findByCuentaCuentaId(cuentaId);
    }

    @Transactional(readOnly = false)
    public Transaccion actualizar(Long id, Transaccion transaccionActualizada) {

        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaccion no encontrada"));
        transaccion.setTipo(transaccionActualizada.getTipo());
        return transaccionRepository.save(transaccion);

    }

    @Transactional(readOnly = false)
    public void eliminar(Long id) {

        if (!transaccionRepository.existsById(id)) {

            throw new RuntimeException("Transaccion no encontrada");
        }

        transaccionRepository.deleteById(id);
    }
}
