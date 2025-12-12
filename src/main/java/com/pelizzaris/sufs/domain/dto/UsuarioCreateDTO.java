package com.pelizzaris.sufs.domain.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record UsuarioCreateDTO(
        @NotNull
        @Size(min = 3, max = 100)
        @NotBlank(message = "O nome do aluno é obrigatório!")
        @Pattern(regexp = "^[A-Za-zÀ-ú ]+$")
        String nomeUsuario,
        @NotNull
        @NotBlank(message = "O e-mail do aluno é obrigatório!")
        @Email
        @Column(unique = true, length = 150, nullable = false)
        String emailUsuario
) {}
