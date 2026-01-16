package com.devsu.hackerearth.backend.account.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CuentaRequest {

    private String numero;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private Long clienteId;

}
