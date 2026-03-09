package com.pelizzaris.sufs.domain.dto;

import com.pelizzaris.sufs.domain.model.util.Roles;
import jakarta.validation.constraints.*;

public record UsuarioUpdateDTO(
        @NotNull
        @Size(min = 3, max = 100)
        @NotBlank(message = "O nome do usuário é obrigatório!")
        @Pattern(regexp = "^[A-Za-zÀ-ú ]+$")
        String nome,
        @NotNull
        @NotBlank(message = "O e-mail do usuário é obrigatório!")
        @Email
        String email,
        Roles roles
) {}
