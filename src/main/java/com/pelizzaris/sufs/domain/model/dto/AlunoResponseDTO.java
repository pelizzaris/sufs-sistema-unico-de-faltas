package com.pelizzaris.sufs.domain.model.dto;

public record AlunoResponseDTO(
        Integer id,
        String nomeAluno,
        Boolean statusAluno,
        Integer turmaId
) {}
