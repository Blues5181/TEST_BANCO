package com.devsu.hackerearth.backend.account.infrastructure.web.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.domain.service.ClienteResumenService;
import com.devsu.hackerearth.backend.account.domain.service.ReporteService;

@RestController
@RequestMapping("/api/transacciones/")
public class ReporterController {

    private final ReporteService reporteService;
    private final ClienteResumenService clienteResumenService;

    @Autowired
    public ReporterController(ReporteService reporteService, ClienteResumenService clienteResumenService) {

        this.reporteService = reporteService;
        this.clienteResumenService = clienteResumenService;
    }

    @GetMapping("clientes/{clienteId}")
    public ResponseEntity<Map<String, Object>> generarReporte(@PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTransaccionStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTransaccionEnd) {

        if (!clienteResumenService.existeclienteResumen(clienteId)) {

            throw new RuntimeException("cliente no Encontrado: " + clienteId);
        }

        Map<String, Object> reporte = reporteService.generarEstadoCuenta(dateTransaccionStart, dateTransaccionEnd,
                clienteId);

        return ResponseEntity.ok(reporte);
    }

}
