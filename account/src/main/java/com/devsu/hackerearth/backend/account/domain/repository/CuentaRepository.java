package com.devsu.hackerearth.backend.account.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.domain.model.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumero(String numero);

    List<Cuenta> findByClienteId(Long clienteId);

}