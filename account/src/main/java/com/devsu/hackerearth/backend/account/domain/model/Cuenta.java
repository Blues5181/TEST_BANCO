
package com.devsu.hackerearth.backend.account.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @Column(name = "cuenta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoInicial;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldoDisponible;

    @Column(nullable = false)
    private Boolean estado;

    @Column(nullable = false)
    private Long clienteId;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaccion> transacciones = new ArrayList<>();

    public Cuenta() {

        this.estado = true;
        this.saldoDisponible = BigDecimal.ZERO;
    }

}
