package com.pelizzaris.sufs.domain.dto;


public record TurmaResponseDTO(
        Long id,
        String nomeTurma,
        String descricaoTurma,
        Boolean statusTurma
) {
}
