package com.devsu.hackerearth.backend.account.domain.model;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "clientes_resumen")
public class ClienteResumen {

    @Id
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizaicon;

    @PrePersist
    @PreUpdate
    public void PrePersist() {
        fechaActualizaicon = LocalDateTime.now();
    }
}
