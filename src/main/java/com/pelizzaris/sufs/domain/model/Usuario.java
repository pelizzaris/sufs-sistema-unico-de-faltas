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
    @NotNull
    @Size(min = 3, max = 100)
    @NotBlank(message = "O nome do aluno é obrigatório!")
    @Pattern(regexp = "^[A-Za-zÀ-ú ]+$")
    private String nomeUsuario;
    @NotNull
    @NotBlank(message = "O e-mail do aluno é obrigatório!")
    @Email
    @Column(unique = true, length = 150, nullable = false)
    private String emailUsuario;
    @NotNull
    private Boolean statusUsuario;
}
