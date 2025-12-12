package com.pelizzaris.sufs.domain.dto;


public record TurmaResponseDTO(
        Integer id,
        String nomeTurma,
        String descricaoTurma,
        Boolean statusTurma
) {
}
