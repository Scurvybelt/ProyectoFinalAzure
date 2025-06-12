package com.E4.cosmos_sql.repository;

import com.E4.cosmos_sql.model.Usuario;
import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCosmosRepository<Usuario, String> {
    @Query("SELECT * FROM Usuarios u WHERE u.correo = @correo")
    Mono<Usuario> findByCorreo(@Param("correo") String correo);
}