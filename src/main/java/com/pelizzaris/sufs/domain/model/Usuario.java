package com.pelizzaris.sufs.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(name = "nome_usuario", length = 150, nullable = false)
    private String nomeUsuario;
    @Column(name = "email_usuario", unique = true, length = 150, nullable = false)
    private String emailUsuario;
    @Column(name = "status_usuario", nullable = false)
    private Boolean statusUsuario;
}
