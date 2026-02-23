package com.pelizzaris.sufs.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FaltaResponseDTO(
        Long id,
        LocalDate dataFalta,
        LocalDateTime dataRegistro,
        String observacaoFalta,
        UUID usuario,
        List<UUID> alunosIds
) {}
