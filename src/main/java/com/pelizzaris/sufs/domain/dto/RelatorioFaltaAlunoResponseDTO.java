package com.pelizzaris.sufs.domain.dto;

import java.time.LocalDate;

public record RelatorioFaltaAlunoResponseDTO(
        LocalDate dataFalta,
        String nome,
        String observacaoFalta
) {}
