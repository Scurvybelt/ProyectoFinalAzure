package com.E4.cosmos_sql.controller;

import com.E4.cosmos_sql.model.FacturasEmitidas;
import com.E4.cosmos_sql.model.Usuario;
import com.E4.cosmos_sql.repository.ClientesRepository;
import com.E4.cosmos_sql.repository.FacturasEmitidasRepository;
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
@RequestMapping("/factu")
public class FacturasController {

    private final FacturasEmitidasRepository facturasEmitidasRepository;
    private final ClientesRepository clientesRepository;
    private final UserRepository usuariosRepository;

    @Autowired
    public FacturasController(FacturasEmitidasRepository facturasEmitidasRepository, ClientesRepository clientesRepository, UserRepository userRepository) {
        this.usuariosRepository = userRepository;
        this.facturasEmitidasRepository = facturasEmitidasRepository;
        this.clientesRepository = clientesRepository;
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> createFactura(@RequestBody FacturasEmitidas factura) {
        return facturasEmitidasRepository.save(factura).map(savedFactura -> {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Factura creada exitosamente");
            response.put("factura", savedFactura);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        });
    }

//    @GetMapping
//    public Flux<Map<String, Object>> getAllFacturas() {
//        return facturasEmitidasRepository.findAll().flatMap(factura -> {
//            return clientesRepository.findById_Cliente(factura.getId_Cliente())
//                    .map(cliente -> {
//                        Map<String, Object> response = new HashMap<>();
//                        response.put("id_Factura", factura.getId_Factura());
//                        response.put("Num_Serie", factura.getNum_Serie());
//                        response.put("name", factura.getName());
//                        response.put("Folio", factura.getFolio());
//                        response.put("UUID", factura.getUUID());
//                        response.put("Base64", factura.getBase64());
//                        response.put("Fecha_Emision", factura.getFecha_Emision());
//                        response.put("Fecha_Timbrado", factura.getFecha_Timbrado());
//                        response.put("id_Cliente", factura.getId_Cliente());
//                        response.put("Nombre_RazonSocial", cliente.getNombre_RazonSocial()); // Include Nombre_RazonSocial
//                        response.put("id_Usuario", factura.getId_Usuario());
//                        response.put("total", factura.getTotal());
//                        response.put("SubTotal", factura.getSubTotal());
//                        response.put("IVA", factura.getIVA());
//                        return response;
//                    });
//        });
//    }

    // Method to get all facturas associated with a specific user
    // Method to get all facturas associated with a specific correo
    @GetMapping("/usuario")
    public Flux<Map<String, Object>> getFacturasByUsuarioCorreo(@RequestParam String correo) {
        return usuariosRepository.findByCorreo(correo)
                .flatMapMany(usuario -> facturasEmitidasRepository.findAll()
                        .filter(factura -> usuario.getId_Usuario().equals(factura.getId_Usuario()))
                        .flatMap(factura -> clientesRepository.findById_Cliente(factura.getId_Cliente())
                                .map(cliente -> {
                                    Map<String, Object> response = new HashMap<>();
                                    response.put("id_Factura", factura.getId_Factura());
                                    response.put("Num_Serie", factura.getNum_Serie());
                                    response.put("name", factura.getName());
                                    response.put("Folio", factura.getFolio());
                                    response.put("UUID", factura.getUUID());
                                    response.put("Base64", factura.getBase64());
                                    response.put("Fecha_Emision", factura.getFecha_Emision());
                                    response.put("Fecha_Timbrado", factura.getFecha_Timbrado());
                                    response.put("id_Cliente", factura.getId_Cliente());
                                    response.put("Nombre_RazonSocial", cliente.getNombre_RazonSocial()); // Include Nombre_RazonSocial
                                    response.put("id_Usuario", factura.getId_Usuario());
                                    response.put("total", factura.getTotal());
                                    response.put("SubTotal", factura.getSubTotal());
                                    response.put("IVA", factura.getIVA());
                                    return response;
                                })
                        )
                );
    }

    // Endpoint to retrieve all facturas without filtering
    @GetMapping("/all")
    public Flux<Map<String, Object>> getAllFacturasWithDetails() {
        return facturasEmitidasRepository.findAll()
                .flatMap(factura -> clientesRepository.findById_Cliente(factura.getId_Cliente())
                        .map(cliente -> {
                            Map<String, Object> response = new HashMap<>();
                            response.put("id_Factura", factura.getId_Factura());
                            response.put("Num_Serie", factura.getNum_Serie());
                            response.put("name", factura.getName());
                            response.put("Folio", factura.getFolio());
                            response.put("UUID", factura.getUUID());
                            response.put("Base64", factura.getBase64());
                            response.put("Fecha_Emision", factura.getFecha_Emision());
                            response.put("Fecha_Timbrado", factura.getFecha_Timbrado());
                            response.put("id_Cliente", factura.getId_Cliente());
                            response.put("Nombre_RazonSocial", cliente.getNombre_RazonSocial()); // Include Nombre_RazonSocial
                            response.put("id_Usuario", factura.getId_Usuario());
                            response.put("total", factura.getTotal());
                            response.put("SubTotal", factura.getSubTotal());
                            response.put("IVA", factura.getIVA());
                            return response;
                        })
                );
    }



//    @GetMapping("/usuario/{id}")
//    public Flux<FacturasEmitidas> getFacturasByUsuarioId(@PathVariable String id) {
//        return facturasEmitidasRepository.findById_Usuario(id);
//    }
}