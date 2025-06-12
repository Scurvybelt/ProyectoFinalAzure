package com.E4.cosmos_sql;

import com.E4.cosmos_sql.model.LoginRequest;
import com.azure.cosmos.implementation.User;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CosmosSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmosSqlApplication.class, args);
	}

}
//
//@Component
//class DataLoader{
//	private final UserRepository repo;
//
//    DataLoader(UserRepository repo) {
//        this.repo = repo;
//    }
//
//
//    @PostConstruct
//	void loadData() {
//		repo.deleteAll().thenMany(Flux.just(new Usuario()));
//	}
//}
//
//@RestController
//@AllArgsConstructor
//class CosmosSqlController {
//	private final UserRepository repo;
//
//	@GetMapping
//	Flux<Usuario> getAll(){
//		return repo.findAll();
//	}
//}
//
//interface UserRepository extends ReactiveCosmosRepository<Usuario, String> {
//
//}
//
//@Container(containerName = "Usuarios")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
//class Usuario {
//	@Id
//	@GeneratedValue
//	private String id_Usuario;
//	private String Nombre;
//	private String Correo;
//	private String Regimen_Fiscal;
//	private String RFC;
//	private String Rol;
//	private String Codigo_Postal;
//	private String Contresña;
//}



