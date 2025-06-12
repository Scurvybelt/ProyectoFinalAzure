package com.E4.cosmos_sql.repository;

import com.E4.cosmos_sql.model.Clientes;
import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientesRepository extends ReactiveCosmosRepository<Clientes, String> {
    @Query("SELECT * FROM Clientes c WHERE c.id_Cliente = @id_Cliente")
    Mono<Clientes> findById_Cliente(@Param("id_Cliente") String id_Cliente);

    @Query("SELECT * FROM Clientes c WHERE c.nombre_RazonSocial = @nombre_RazonSocial")
    Mono<Clientes> findByNombre_RazonSocial(@Param("nombre_RazonSocial") String nombre_RazonSocial);
}
