package com.devsu.hackerearth.backend.account.domain.exception;

public class SaldoNoDisponibleException extends RuntimeException {

    public SaldoNoDisponibleException(String mensaje) {

        super(mensaje);

    }

}
