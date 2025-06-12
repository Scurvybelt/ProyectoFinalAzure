package com.E4.cosmos_sql.controller;

import com.E4.cosmos_sql.model.Clientes;
import com.E4.cosmos_sql.repository.ClientesRepository;
import com.E4.cosmos_sql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClientesController {
    private final ClientesRepository clientesRepository;
    private final UserRepository usuarioRepository;

    @Autowired
    public ClientesController(ClientesRepository clientesRepository, UserRepository usuarioRepository) {
        this.clientesRepository = clientesRepository;
        this.usuarioRepository = usuarioRepository;

    }



    @GetMapping("/userbycorreo")
    public Flux<Clientes> getClientesByUsuarioCorreo(@RequestParam String correo) {
        return usuarioRepository.findByCorreo(correo)
                .flatMapMany(usuario -> clientesRepository.findAll()
                        .filter(cliente -> cliente.getId_Usuario() != null && cliente.getId_Usuario().equals(usuario.getId_Usuario()))
                );
    }

    @GetMapping
    public Flux<Clientes> getAllClientes() {
        return clientesRepository.findAll();
    }



    @GetMapping("/{id}")
    public Mono<ResponseEntity<Clientes>> getClienteById(@PathVariable String id) {
        return clientesRepository.findById_Cliente(id)
                .map(cliente -> ResponseEntity.ok(cliente))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> createCliente(@RequestBody Clientes cliente) {
        return clientesRepository.findByNombre_RazonSocial(cliente.getNombre_RazonSocial()) // Check if a client with the same nombre_RazonSocial exists
                .flatMap(existingCliente -> {
                    // If a client already exists, return a conflict response
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "error");
                    response.put("message", "El cliente que quieres dar de alta ya lo tienes o alguien mas ya lo tiene asignado");
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
                })
                .switchIfEmpty(
                        // If no such client exists, create the new client
                        Mono.defer(() -> {
                            cliente.setFecha_Registro(java.time.LocalDate.now()); // Set the current date
                            return clientesRepository.save(cliente).map(savedCliente -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("status", "success");
                                response.put("message", "Cliente creado exitosamente");
                                response.put("cliente", savedCliente);
                                return ResponseEntity.status(HttpStatus.CREATED).body(response);
                            });
                        })
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> updateCliente(@PathVariable String id, @RequestBody Clientes cliente) {
        return clientesRepository.findById(id)
                .flatMap(existingCliente -> {
                    cliente.setId_Cliente(existingCliente.getId_Cliente());
                    cliente.setFecha_Registro(existingCliente.getFecha_Registro()); // Preserve original registration date
                    return clientesRepository.save(cliente);
                })
                .map(updatedCliente -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("message", "Cliente actualizado exitosamente");
                    response.put("cliente", updatedCliente);
                    return ResponseEntity.ok(response);
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> deleteCliente(@PathVariable String id) {
//        return clientesRepository.findById(id)
//                .flatMap(existingCliente -> clientesRepository.deleteById(id)
//                        .then(Mono.just(ResponseEntity.noContent().build())))
//                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
//    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCliente(@PathVariable String id) {
        return clientesRepository.deleteById(id);
    }

}