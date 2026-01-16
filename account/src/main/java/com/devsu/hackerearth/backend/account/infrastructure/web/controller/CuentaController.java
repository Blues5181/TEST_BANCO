package com.devsu.hackerearth.backend.account.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.domain.model.Cuenta;
import com.devsu.hackerearth.backend.account.domain.service.CuentaService;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.CuentaRequest;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.CuentaResponse;
import com.devsu.hackerearth.backend.account.infrastructure.web.mapper.CuentaMapper;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {

        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> crear(@RequestBody CuentaRequest request) {

        Cuenta cuenta = CuentaMapper.toEntity(request);
        Cuenta cuentaCreada = cuentaService.crear(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(CuentaMapper.toResponse(cuentaCreada));

    }

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> obtenerTodas() {

        List<CuentaResponse> cuentas = cuentaService.obtenerTodas().stream()
                .map(CuentaMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> obtenerPorId(@PathVariable Long id) {

        return cuentaService.obtenerPorId(id)
                .map(cuenta -> ResponseEntity.ok(CuentaMapper.toResponse(cuenta)))
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponse> actualizar(@PathVariable Long id, @RequestBody CuentaRequest request) {

        Cuenta cuenta = CuentaMapper.toEntity(request);
        Cuenta cuentaActualizada = cuentaService.actualizar(id, cuenta);

        return ResponseEntity.ok(CuentaMapper.toResponse(cuentaActualizada));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<CuentaResponse> actualizarParcial(@PathVariable Long id, @RequestBody CuentaRequest request) {
        Cuenta cuentaExistente = cuentaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (request.getTipo() != null) {

            cuentaExistente.setTipo(request.getTipo());
        }

        if (request.getEstado() != null) {

            cuentaExistente.setEstado(request.getEstado());

        }

        Cuenta cuentaActualizada = cuentaService.actualizar(id, cuentaExistente);

        return ResponseEntity.ok(CuentaMapper.toResponse(cuentaActualizada));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        cuentaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

}
