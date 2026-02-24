package com.pelizzaris.sufs.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AlunoUpdateDTO(
        @NotNull
        @Size(min = 3, max = 100)
        @NotBlank(message = "O nome do aluno é obrigatório!")
        @Pattern(regexp = "^[A-Za-zÀ-ú ]+$")
        String nome,
        @NotBlank(message = "O e-mail é obrigatório!")
        String email,
        @NotNull(message = "O status do aluno é obrigatório!")
        Boolean status,
        @NotNull(message = "A turma é obrigatória!")
        Long turmaId
) {}
