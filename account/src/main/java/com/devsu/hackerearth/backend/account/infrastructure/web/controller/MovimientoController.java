package com.devsu.hackerearth.backend.account.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devsu.hackerearth.backend.account.domain.model.Cuenta;
import com.devsu.hackerearth.backend.account.domain.model.Transaccion;
import com.devsu.hackerearth.backend.account.domain.service.CuentaService;
import com.devsu.hackerearth.backend.account.domain.service.TransaccionService;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.TransaccionRequest;
import com.devsu.hackerearth.backend.account.infrastructure.web.dto.TransaccionResponse;
import com.devsu.hackerearth.backend.account.infrastructure.web.mapper.CuentaMapper;

@Controller
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final TransaccionService transaccionService;
    private final CuentaService cuentaService;

    @Autowired
    public MovimientoController(TransaccionService transaccionService, CuentaService cuentaService) {

        this.transaccionService = transaccionService;
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<TransaccionResponse> crear(@RequestBody TransaccionRequest request) {
        Cuenta cuenta = cuentaService.obtenerPorId(request.getCuentaId())
                .orElseThrow(() -> new RuntimeException("cuenta no encontrada"));

        Transaccion transaccion = CuentaMapper.toEntity(request, cuenta);
        Transaccion transaccionCreada = transaccionService.crear(transaccion);

        return ResponseEntity.status(HttpStatus.CREATED).body(CuentaMapper.toResponse(transaccionCreada));

    }

    @GetMapping
    public ResponseEntity<List<TransaccionResponse>> obtenerTodas() {

        List<TransaccionResponse> transacciones = transaccionService.obtenerTodas().stream()
                .map(CuentaMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transacciones);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponse> obtenerPorId(@PathVariable Long id) {

        return transaccionService.obtenerPorId(id)
                .map(transaccion -> ResponseEntity.ok(CuentaMapper.toResponse(transaccion)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionResponse> actualizar(@PathVariable Long id,@RequestBody TransaccionRequest request)
    {

Cuenta cuenta = cuentaService.obtenerPorId(request.getCuentaId()).orElseThrow(
()-> new RuntimeException("cuenta no encontrada"));

Transaccion transaccion = CuentaMapper.toEntity(request,cuenta);
Transaccion transaccionActualizada = transaccionService.actualizar(id,transaccion);
return ResponseEntity.ok(CuentaMapper.toResponse(transaccionActualizada));


    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransaccionResponse> actualizarParcial(@PathVariable Long id,
            @RequestBody TransaccionRequest request) {

        Transaccion transaccionExistente = transaccionService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Transaccion no encontrada"));

        if (request.getTipo() != null) {

            transaccionExistente.setTipo(request.getTipo());
        }
        Transaccion transaccionActualizada = transaccionService.actualizar(id, transaccionExistente);
        return ResponseEntity.ok(CuentaMapper.toResponse(transaccionActualizada));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        transaccionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

}
