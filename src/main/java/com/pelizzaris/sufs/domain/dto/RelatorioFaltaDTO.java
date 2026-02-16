package com.pelizzaris.sufs.domain.dto;

import java.time.LocalDate;

public record RelatorioFaltaDTO(
        LocalDate dataFalta,
        String nomeAluno,
        String observacaoFalta
) {}
