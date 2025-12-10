package com.pelizzaris.sufs.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlunoCreateDTO(
        @NotNull
        @Size(min = 3, max = 100)
        @NotBlank(message = "O nome do aluno é obrigatório!")
        @Pattern(regexp = "^[A-Za-zÀ-ú ]+$")
        String nomeAluno,
        @NotNull(message = "A turma é obrigatória!")
        Integer turmaId
) {}
