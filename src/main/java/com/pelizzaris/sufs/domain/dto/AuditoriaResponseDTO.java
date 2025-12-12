package com.pelizzaris.sufs.domain.dto;

import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;

import java.time.LocalDateTime;

public record AuditoriaResponseDTO(
        Integer id,
        LocalDateTime dataRegistro,
        AcaoAuditoria acaoRealizada,
        Integer faltaId,
        Integer usuaroId
) {}
