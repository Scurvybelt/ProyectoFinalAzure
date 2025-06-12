package com.E4.cosmos_sql.repository;

import com.E4.cosmos_sql.model.Auditoria;
import com.E4.cosmos_sql.model.Clientes;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import com.azure.spring.data.cosmos.repository.Query;

@Repository
public interface AuditoriaRepository extends ReactiveCosmosRepository<Auditoria, String> {

    // Find by user ID
    @Query("SELECT a FROM Auditoria a WHERE a.id_Usuario = :id_Usuario")
    Flux<Auditoria> findById_Usuario(String id_Usuario);

    // Find by client ID
    @Query("SELECT a FROM Auditoria a WHERE a.id_Cliente = :id_Cliente")
    Flux<Auditoria> findById_Cliente(String id_Cliente);

    // Find by action
    @Query("SELECT a FROM Auditoria a WHERE a.accion = :accion")
    Flux<Auditoria> findByAccion(String accion);
}
