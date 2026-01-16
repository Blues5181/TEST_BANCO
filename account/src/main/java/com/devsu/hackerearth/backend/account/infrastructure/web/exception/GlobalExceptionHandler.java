package com.devsu.hackerearth.backend.account.infrastructure.web.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.devsu.hackerearth.backend.account.domain.exception.SaldoNoDisponibleException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SaldoNoDisponibleException.class)
    public ResponseEntity<Map<String, Object>> manejarSaldoNoDisponibleException(SaldoNoDisponibleException ex) {
        logger.warn("Saldo No Disponbile Capturado: {}", ex.getMessage(), ex);

        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        error.put("estado", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> manejarRuntimeException(RuntimeException ex) {

        logger.warn("RuntimeException capturada_ {}", ex.getMessage(), ex);

        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        error.put("estado", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarException(Exception ex) {

        logger.warn("Excepcion no controlada capturada_ {}", ex.getMessage(), ex);

        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", "Error interno del servidor");
        error.put("timestamp", LocalDateTime.now());
        error.put("estado", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }

}
