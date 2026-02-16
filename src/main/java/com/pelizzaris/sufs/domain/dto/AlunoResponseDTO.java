package com.pelizzaris.sufs.domain.dto;

public record AlunoResponseDTO(
        String nomeAluno,
        String emailAluno,
        Boolean statusAluno,
        Long turmaId
) {}
