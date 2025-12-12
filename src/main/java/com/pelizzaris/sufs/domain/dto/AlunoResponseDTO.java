package com.pelizzaris.sufs.domain.dto;

public record AlunoResponseDTO(
        Integer id,
        String nomeAluno,
        Boolean statusAluno,
        Integer turmaId
) {}
