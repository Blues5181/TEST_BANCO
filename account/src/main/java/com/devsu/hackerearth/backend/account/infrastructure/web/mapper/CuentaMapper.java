package com.devsu.hackerearth.backend.account.infrastructure.web.mapper;

import com.devsu.hackerearth.backend.account.domain.model.Cuenta;
import com.devsu.hackerearth.backend.account.domain.model.Transaccion;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.CuentaRequest;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.CuentaResponse;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.TransaccionRequest;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.TransaccionResponse;

public class CuentaMapper {

    public static Cuenta toEntity(CuentaRequest request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero(request.getNumero());
        cuenta.setTipo(request.getTipo());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setEstado(request.getEstado() != null ? request.getEstado() : true);
        cuenta.setClienteId(request.getClienteId());
        return cuenta;

    }

    public static CuentaResponse toResponse(Cuenta cuenta) {

        CuentaResponse response = new CuentaResponse();
        response.setCuentaId(cuenta.getCuentaId());
        response.setNumero(cuenta.getNumero());
        response.setTipo(cuenta.getTipo());
        response.setSaldoInicial(cuenta.getSaldoInicial());
        response.setSaldoDisponible(cuenta.getSaldoDisponible());
        response.setEstado(cuenta.getEstado());
        response.setClienteId(cuenta.getClienteId());
        return response;
    }

    public static Transaccion toEntity(TransaccionRequest request, Cuenta cuenta) {

        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(request.getTipo());
        transaccion.setValor(request.getValor());
        transaccion.setCuenta(cuenta);

        return transaccion;

    }

    public static TransaccionResponse toResponse(Transaccion transaccion) {

        TransaccionResponse response = new TransaccionResponse();

        response.setTransaccionId(transaccion.getTransaccionId());
        response.setFecha(transaccion.getFecha());
        response.setTipo(transaccion.getTipo());
        response.setValor(transaccion.getValor());
        response.setSaldo(transaccion.getSaldo());
        response.setCuentaId(transaccion.getCuenta().getCuentaId());

        return response;
    }

}
