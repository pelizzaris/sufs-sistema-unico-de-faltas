package com.pelizzaris.sufs.domain.dto;

import java.time.LocalDate;

public record RelatorioFaltaResponseDTO(
        LocalDate dataFalta,
        String nomeAluno,
        String observacaoFalta
) {}
