package com.E4.cosmos_sql.controller;

import com.E4.cosmos_sql.model.Clientes;
import com.E4.cosmos_sql.model.LoginRequest;
import com.E4.cosmos_sql.model.Usuario;
import com.E4.cosmos_sql.repository.UserRepository;
import com.E4.cosmos_sql.utils.img.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")

public class UsuariosController {
    private final UserRepository userRepository;

    @Autowired
    public UsuariosController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<Usuario> getAllUsuarios() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Usuario> getUsuarioById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @GetMapping("/current")
    public Mono<ResponseEntity<Map<String, Object>>> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            // Extraer el token del header "Bearer <token>"
            String jwtToken = token.substring(7);

            // Validar el token
            if (!JwtUtil.validateToken(jwtToken)) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "Token inválido o expirado");
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
            }

            // Obtener el correo del token usando getSubjectFromToken
            String correo = JwtUtil.getSubjectFromToken(jwtToken);

            return userRepository.findByCorreo(correo)
                    .map(usuario -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("status", "success");
                        response.put("correo", usuario.getCorreo());
                        response.put("nombre", usuario.getNombre());
                        return ResponseEntity.ok(response);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("status", "error");
                        response.put("message", "Usuario no encontrado");
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
                    }));
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Error al procesar el token");
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
        }
    }
    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> createUsuario(@RequestBody Usuario usuario) {
        return userRepository.save(usuario).map(savedUsuario -> {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Usuario creado exitosamente");
            response.put("usuario", savedUsuario.getCorreo());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        });

    }


    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, Object>>> login(@RequestBody LoginRequest loginRequest) {
        return userRepository.findByCorreo(loginRequest.getCorreo())
                .flatMap(usuario -> {
                    Map<String, Object> response = new HashMap<>();
                    if (usuario.getContraseña().equals(loginRequest.getContraseña())) {
                        // Generate JWT token
                        String token = JwtUtil.generateToken(usuario.getCorreo());

                        response.put("status", "success");
                        response.put("message", "Login exitoso");
                        response.put("usuario", usuario.getCorreo());
                        response.put("token", token); // Include the token in the response

                        return Mono.just(ResponseEntity.ok(response));
                    } else {
                        response.put("status", "error");
                        response.put("message", "Contraseña incorrecta");
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response));
                    }
                })
                .switchIfEmpty(Mono.defer(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "error");
                    response.put("message", "Usuario no encontrado");
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
                }));
    }

    @GetMapping("/idByCorreo")
    public Mono<ResponseEntity<Map<String, Object>>> getIdByCorreo(@RequestParam String correo) {
        return userRepository.findByCorreo(correo)
                .map(usuario -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "success");
                    response.put("id", usuario.getId_Usuario());
                    response.put("nombre", usuario.getNombre());
                    return ResponseEntity.ok(response);
                })
                .switchIfEmpty(Mono.defer(() -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "error");
                    response.put("message", "Usuario no encontrado");
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
                }));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> updateUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        return userRepository.findById(id)
                .flatMap(existingUsuario -> {
                    // Actualiza solo los campos permitidos, manteniendo la contraseña si no se proporciona una nueva
                    if (usuario.getNombre() != null) {
                        existingUsuario.setNombre(usuario.getNombre());
                    }
                    if (usuario.getCorreo() != null) {
                        existingUsuario.setCorreo(usuario.getCorreo());
                    }
                    if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
                        existingUsuario.setContraseña(usuario.getContraseña());
                    }
                    // Actualiza otros campos según sea necesario

                    return userRepository.save(existingUsuario)
                            .map(updatedUsuario -> {
                                Map<String, Object> response = new HashMap<>();
                                response.put("status", "success");
                                response.put("message", "Usuario actualizado exitosamente");
                                return ResponseEntity.ok(response);
                            });
                })
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUsuario(@PathVariable String id) {
        return userRepository.deleteById(id);
    }
}
