package com.pelizzaris.sufs.domain.dto;

import jakarta.validation.constraints.*;

public record AlunoUpdateDTO(
        @NotNull
        @Size(min = 3, max = 100)
        @NotBlank(message = "O nome do aluno é obrigatório!")
        @Pattern(regexp = "^[A-Za-zÀ-ú ]+$")
        String nome,
        @NotNull
        @NotBlank(message = "O e-mail do aluno é obrigatório!")
        @Email
        String email,
        @NotNull(message = "A turma é obrigatória!")
        Long turmaId
) {}
