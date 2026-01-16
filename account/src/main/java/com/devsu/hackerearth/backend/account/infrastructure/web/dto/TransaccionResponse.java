package com.devsu.hackerearth.backend.account.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransaccionResponse {

    private Long transaccionId;
    private LocalDateTime fecha;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Long cuentaId;

}