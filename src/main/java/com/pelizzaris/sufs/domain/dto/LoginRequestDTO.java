package com.pelizzaris.sufs.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotNull
        @NotBlank(message = "O e-mail do usuário é obrigatório!")
        @Email
        String email,
        String senha
) {}
