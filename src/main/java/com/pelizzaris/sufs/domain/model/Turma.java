package com.pelizzaris.sufs.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_turma")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    @NotNull
    @NotBlank(message = "O nome do aluno é necessário!")
    private String nomeTurma;
    private String descricaoTurma;
    @NotNull
    private Boolean statusTurma;
}
