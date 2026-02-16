package com.pelizzaris.sufs.domain.dto;

import java.util.UUID;

public record FaltaAlunoResponseDTO(
        Long id,
        Long faltaId,
        UUID alunoId
) {}
