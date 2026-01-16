package com.devsu.hackerearth.backend.account.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransaccionRequest {
    private String tipo;
    private BigDecimal valor;
    private Long cuentaId;

}
