package com.pelizzaris.sufs.domain.dto;

import com.pelizzaris.sufs.validation.DataRetroativa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FaltaUpdateDTO(
        @NotNull(message = "A data da falta é obrigatória!")
        @DataRetroativa(days = 3, message = "A data da falta só pode ter até 3 dias retroativos")
        LocalDate dataFalta,
        @Size(max = 255)
        String observacaoFalta,
        @NotNull(message = "O usuário é obrigatório!")
        UUID usuarioId,
        @NotNull(message = "A lista de alunos não pode ser nula")
        List<UUID> alunosIds
) {}
