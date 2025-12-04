package com.pelizzaris.sufs.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private  Integer ID;
    @NotNull
    @NotBlank(message = "O nome do aluno é necessário!")
    private String nomeUsuario;
    @NotNull
    @NotBlank(message = "O e-mail do aluno é necessário!")
    @Email
    private String emailUsuario;
    @NotNull
    private Boolean statusUsuario;
}
