package com.pelizzaris.sufs.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TurmaUpdateDTO(
        @NotNull
        @Size(min = 3, max = 30)
        @NotBlank(message = "O nome da turma é obrigatório!")
        String nomeTurma,
        @Size(max = 255)
        String descricaoTurma,
        @NotNull
        Boolean statusTurma
) {}
