package com.pelizzaris.sufs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Vinícius Pelizzari
 * @version 1.0
 */
@SpringBootApplication
public class SufsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SufsApplication.class, args);
		System.out.println("Aplicação rodando!");
	}

	/*
	* ORDEM PARA DESENVOLVIMENTO
	* model
	* entities
	* dto
	* mapper
	* repositories
	* services
	* controller
	* test*/

}
