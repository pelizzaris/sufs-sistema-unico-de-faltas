package com.pelizzaris.sufs.domain.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record UsuarioCreateDTO(
        @NotNull
        @Size(min = 3, max = 100)
        @NotBlank(message = "O nome do usuário é obrigatório!")
        @Pattern(regexp = "^[A-Za-zÀ-ú ]+$")
        String nome,
        @NotNull
        @NotBlank(message = "O e-mail do usuário é obrigatório!")
        @Email
        @Column(unique = true, length = 150, nullable = false)
        String email
) {}
