package com.devsu.hackerearth.backend.account.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.domain.model.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    @Query("SELECT t FROM Transaccion t WHERE t.cuenta.clienteId = :clienteId AND t.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Transaccion> findByCuentaClienteIdAndFechaBetween(
            @Param("clienteId") Long clienteId,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin);

    List<Transaccion> findByCuentaCuentaId(Long cuentaId);

}