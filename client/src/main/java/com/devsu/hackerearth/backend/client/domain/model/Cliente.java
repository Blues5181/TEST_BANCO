package com.devsu.hackerearth.backend.client.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona {

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    public Cliente() {
        this.estado = true;
    }

    public Long getClienteId() {

        return this.getId();
    }

    public void setClienteId(Long clienteId) {

    }
}
