package com.E4.cosmos_sql.controller;

import com.E4.cosmos_sql.model.Administrador;
import com.E4.cosmos_sql.model.LoginRequest;
import com.E4.cosmos_sql.model.Usuario;
import com.E4.cosmos_sql.repository.AdminRepository;
import com.E4.cosmos_sql.utils.img.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdministradorController {
    private final AdminRepository adminRepository;

    @Autowired
    public AdministradorController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @GetMapping
    public Flux<Administrador> getAllAdmins() {
        return adminRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Administrador> getUsuarioById(@PathVariable String id) {
        return adminRepository.findById(id);
    }

    @GetMapping("/current")
    public Mono<ResponseEntity<Map<String, Object>>> getCurrentAdmin(@RequestHeader("Authorization") String token) {
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

            return adminRepository.findByCorreo(correo)
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
    //    @PostMapping
//    public Mono<ResponseEntity<Map<String, Object>>> createUsuario(@RequestBody Usuario admin) {
//        return userRepository.save(admin).map(savedUsuario -> {
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Usuario creado exitosamente");
//            response.put("admin", savedUsuario.getCorreo());
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        });
//
//    }
    @PostMapping("/register")
    public Mono<ResponseEntity<Map<String, Object>>> registerAdmin(@RequestBody Administrador admin) {

        if (admin.getNombre() == null || admin.getCorreo() == null || admin.getContraseña() == null ||
                admin.getNombre().isEmpty() || admin.getCorreo().isEmpty() || admin.getContraseña().isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Todos los campos son obligatorios");
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse));
        }

        return adminRepository.save(admin).map(savedAdmin -> {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Administrador registrado exitosamente");
            response.put("admin", savedAdmin.getCorreo());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, Object>>> login(@RequestBody LoginRequest loginRequest) {
        return adminRepository.findByCorreo(loginRequest.getCorreo())
                .flatMap(admin -> {
                    Map<String, Object> response = new HashMap<>();
                    if (admin.getContraseña().equals(loginRequest.getContraseña())) {
                        // Generate JWT token
                        String token = JwtUtil.generateToken(admin.getCorreo());

                        response.put("status", "success");
                        response.put("message", "Login exitoso");
                        response.put("admin", admin.getCorreo());
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





//    @PutMapping("/{id}")
//    public Mono<Usuario> updateUsuario(@PathVariable String id, @RequestBody Usuario admin) {
//        return userRepository.findById(id)
//                .flatMap(existingUsuario -> {
//                    admin.setId_Usuario(existingUsuario.getId_Usuario());
//                    return userRepository.save(admin);
//                });
//    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAdmin(@PathVariable String id) {
        return adminRepository.deleteById(id);
    }
}
