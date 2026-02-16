package com.pelizzaris.sufs.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FaltaAlunoCreateDTO(
        @NotNull(message = "O referenciamento da falta é obrigatório!")
        Long faltaId,
        @NotNull(message = "O referenciamento do aluno é obrigatório!")
        UUID alunoId
) {}
