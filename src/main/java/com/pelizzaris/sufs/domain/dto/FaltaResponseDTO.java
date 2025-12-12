package com.pelizzaris.sufs.domain.dto;

import com.pelizzaris.sufs.domain.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FaltaResponseDTO(
        Integer id,
        LocalDate dataFalta,
        LocalDateTime dataRegistro,
        String observacaoFalta,
        Usuario usuario
) {}
