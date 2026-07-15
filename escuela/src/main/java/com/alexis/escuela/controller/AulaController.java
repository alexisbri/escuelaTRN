package com.alexis.escuela.controller;

import com.alexis.escuela.dto.aula.AulaRequest;
import com.alexis.escuela.dto.aula.AulaResponse;
import com.alexis.escuela.services.aula.AulaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aula")
@AllArgsConstructor
@Validated
public class AulaController {

    private final AulaService aulaService;

    @GetMapping
    public ResponseEntity<List<AulaResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer capacidad) {
        return ResponseEntity.ok(aulaService.listar(nombre, capacidad));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponse> obtenerPorId(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id) {
        return ResponseEntity.ok(aulaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<AulaResponse> registrar(
            @Valid @RequestBody AulaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aulaService.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AulaResponse> actualizar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @Valid @RequestBody AulaRequest request) {
        return ResponseEntity.ok(aulaService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id) {
        aulaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
