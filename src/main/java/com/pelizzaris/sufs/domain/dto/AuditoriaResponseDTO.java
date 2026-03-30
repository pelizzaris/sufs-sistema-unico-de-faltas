package com.pelizzaris.sufs.domain.dto;

import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;

import java.time.LocalDateTime;
import java.util.UUID;

public record AuditoriaResponseDTO(
        Long id,
        LocalDateTime dataRegistro,
        AcaoAuditoria acaoRealizada,
        String entidadeId,
        UUID usuario
) {}
