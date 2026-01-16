package com.devsu.hackerearth.backend.account.domain.model;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "transacciones")
public class Transaccion {

    @Id
    @Column(name = "transaccion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaccionId;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    @PrePersist
    public void PrePersist() {

        if (fecha == null) {

            fecha = LocalDateTime.now();
        }
    }
}