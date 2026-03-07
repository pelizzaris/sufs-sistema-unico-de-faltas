package com.pelizzaris.sufs.domain.dto;

import java.util.UUID;

public record AlunoResponseDTO(
        UUID id,
        String nome,
        String email,
        Boolean status,
        Long turmaId
) {}
