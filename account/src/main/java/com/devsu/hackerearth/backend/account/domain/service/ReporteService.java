package com.devsu.hackerearth.backend.account.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.domain.model.Cuenta;
import com.devsu.hackerearth.backend.account.domain.model.Transaccion;
import com.devsu.hackerearth.backend.account.domain.repository.CuentaRepository;
import com.devsu.hackerearth.backend.account.domain.repository.TransaccionRepository;

@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    @Autowired
    public ReporteService(CuentaRepository cuentaRepository, TransaccionRepository transaccionRepository) {

        this.cuentaRepository = cuentaRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> generarEstadoCuenta(LocalDate fechaInicio, LocalDate fechaFin, Long clienteId) {

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        List<Transaccion> transacciones = transaccionRepository.findByCuentaClienteIdAndFechaBetween(clienteId, inicio, fin);

        Map<String, Object> reporte = new HashMap<>();

        reporte.put("clienteId", clienteId);
        reporte.put("fechaInicio", fechaInicio);
        reporte.put("fechaFin", fechaFin);

        List<Map<String, Object>> cuentasData = cuentas.stream().map(cuenta ->

        {
            Map<String, Object> cuentaData = new HashMap<>();

            cuentaData.put("cuentaId", cuenta.getCuentaId());
            cuentaData.put("numero", cuenta.getNumero());
            cuentaData.put("tipo", cuenta.getTipo());
            cuentaData.put("saldoInicial", cuenta.getSaldoInicial());
            cuentaData.put("sladoDisponible", cuenta.getEstado());

            List<Map<String, Object>> movimientos = transacciones.stream()
                    .filter(t -> t.getCuenta().getCuentaId().equals(cuenta.getCuentaId()))
                    .map(t -> {
                        Map<String, Object> movimiento = new HashMap<>();
                        movimiento.put("transaccionId", t.getTransaccionId());
                        movimiento.put("fecha", t.getFecha());
                        movimiento.put("tipo", t.getTipo());
                        movimiento.put("valor", t.getValor());
                        movimiento.put("saldo", t.getSaldo());
                        return movimiento;
                    })
                    .collect(Collectors.toList());
            cuentaData.put("movimientos", movimientos);
            return cuentaData;
        }).collect(Collectors.toList());

        reporte.put("cuentas", cuentasData);
        return reporte;
    }

}
