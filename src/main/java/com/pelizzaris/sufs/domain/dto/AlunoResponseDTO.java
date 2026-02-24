package com.pelizzaris.sufs.domain.dto;

public record AlunoResponseDTO(
        String nome,
        String email,
        Boolean status,
        Long turmaId
) {}
