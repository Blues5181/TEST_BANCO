package com.devsu.hackerearth.backend.account.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.domain.model.Cuenta;
import com.devsu.hackerearth.backend.account.domain.repository.CuentaRepository;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional(readOnly = false)
    public Cuenta crear(Cuenta cuenta) {

        if (cuentaRepository.findByNumero(cuenta.getNumero()).isPresent()) {

            throw new RuntimeException("Ya eiste una cuenta con este numero");
        }

        cuenta.setSaldoDisponible(cuenta.getSaldoInicial());

        return cuentaRepository.save(cuenta);

    }

    @Transactional(readOnly = true)
    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();

    }

    @Transactional(readOnly = true)
    public Optional<Cuenta> obtenerPorId(Long id) {

        return cuentaRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public Cuenta actualizar(Long id, Cuenta cuentaActualizada) {

        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        cuenta.setTipo(cuentaActualizada.getTipo());
        cuenta.setEstado(cuentaActualizada.getEstado());

        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public void eliminar(Long id) {

        if (!cuentaRepository.existsById(id)) {

            throw new RuntimeException("Cuenta no encontrada");
        }

        cuentaRepository.deleteById(id);
    }

}
