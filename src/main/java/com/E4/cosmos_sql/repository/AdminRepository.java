package com.E4.cosmos_sql.repository;

import com.E4.cosmos_sql.model.Administrador;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AdminRepository extends ReactiveCosmosRepository<Administrador, String> {
    Mono<Administrador> findByCorreo(String correo);
}