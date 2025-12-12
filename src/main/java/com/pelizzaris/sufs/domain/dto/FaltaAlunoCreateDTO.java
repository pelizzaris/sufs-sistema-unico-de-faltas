package com.pelizzaris.sufs.domain.dto;

import jakarta.validation.constraints.NotNull;

public record FaltaAlunoCreateDTO(
        @NotNull(message = "O referenciamento da falta é obrigatório!")
        Integer faltaId,
        @NotNull(message = "O referenciamento do aluno é obrigatório!")
        Integer alunoId
) {}
