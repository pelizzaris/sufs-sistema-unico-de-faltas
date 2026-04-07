package com.pelizzaris.sufs.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FaltaResponseDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataFalta,
        LocalDateTime dataRegistro,
        String observacaoFalta,
        UUID usuarioId,
        List<UUID> alunosIds
) {}
