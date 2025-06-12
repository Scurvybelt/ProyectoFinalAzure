package com.E4.cosmos_sql.controller;


import com.E4.cosmos_sql.model.Auditoria;
import com.E4.cosmos_sql.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {
    private final AuditoriaRepository auditoriaRepository;

    @Autowired
    public AuditoriaController(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    // 1. Crear una nueva auditoría
    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> createAuditoria(@RequestBody Auditoria auditoria) {
        auditoria.setFechaMovimiento(java.time.LocalDate.now()); // Fecha actual
        return auditoriaRepository.save(auditoria)
                .map(savedAuditoria -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("message", "Auditoría creada exitosamente");
                    response.put("data", savedAuditoria);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }

    // 2. Obtener todas las auditorías
    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> getAllAuditorias() {
        return auditoriaRepository.findAll()
                .collectList()
                .map(auditorias -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("message", "Lista de auditorías obtenida exitosamente");
                    response.put("data", auditorias);
                    return ResponseEntity.ok(response);
                });
    }

    // 3. Obtener auditorías por ID de usuario
    @GetMapping("/byUser")
    public Mono<ResponseEntity<Map<String, Object>>> getAuditoriasByUsuarioId(@RequestParam String id_Usuario) {
        return auditoriaRepository.findById_Usuario(id_Usuario)
                .collectList()
                .map(auditorias -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("message", "Auditorías filtradas por usuario obtenidas exitosamente");
                    response.put("data", auditorias);
                    return ResponseEntity.ok(response);
                });
    }

    // 4. Obtener auditorías por ID de cliente
    @GetMapping("/byClient")
    public Mono<ResponseEntity<Map<String, Object>>> getAuditoriasByClienteId(@RequestParam String id_Cliente) {
        return auditoriaRepository.findById_Cliente(id_Cliente)
                .collectList()
                .map(auditorias -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("message", "Auditorías filtradas por cliente obtenidas exitosamente");
                    response.put("data", auditorias);
                    return ResponseEntity.ok(response);
                });
    }

    // 5. Obtener auditorías por tipo de acción
    @GetMapping("/byAction")
    public Mono<ResponseEntity<Map<String, Object>>> getAuditoriasByAccion(@RequestParam String accion) {
        return auditoriaRepository.findByAccion(accion)
                .collectList()
                .map(auditorias -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("message", "Auditorías filtradas por acción obtenidas exitosamente");
                    response.put("data", auditorias);
                    return ResponseEntity.ok(response);
                });
    }

}
