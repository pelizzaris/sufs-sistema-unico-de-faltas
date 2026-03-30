package com.pelizzaris.sufs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Vinícius Pelizzari
 * @version 1.0
 */
@SpringBootApplication
public class SufsApplication {
	static void main(String[] args) {

		//configurar rotas e permissões no controller, getIDusuario para auditoria nos métodos de banco e permissões conforme criador

		SpringApplication.run(SufsApplication.class, args);
		System.out.println("Aplicação rodando!");
	}
}
