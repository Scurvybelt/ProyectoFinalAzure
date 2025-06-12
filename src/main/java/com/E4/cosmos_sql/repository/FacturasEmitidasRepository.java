package com.E4.cosmos_sql.repository;


import com.E4.cosmos_sql.model.FacturasEmitidas;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FacturasEmitidasRepository extends ReactiveCosmosRepository<FacturasEmitidas, String> {
//    Flux<FacturasEmitidas> findById_Usuario(String id_Usuario);
}