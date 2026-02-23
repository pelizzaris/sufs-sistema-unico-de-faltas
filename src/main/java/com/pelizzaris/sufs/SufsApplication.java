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
	* --- model/entities
	* --- dto
	* --- mapper
	* --- repositories
    * services
    * controller
    * test
	*
	* pom - security
	* */

	/*
	* auditoria
	* docker
	* cache
	* */

    /*
     * Integrar WhatsApp para envio automático de mensagens para os alunos faltosos.
     * Criar validação de e-mail
     * Ao criar ou salvar é necessário validar o status ou se já existe
     * ajustar Roles no model e repository de Usuario
     */

}
