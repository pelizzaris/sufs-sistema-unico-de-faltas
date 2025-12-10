package com.pelizzaris.sufs.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Integer id;
    @NotNull
    @Size(min = 3, max = 30)
    @NotBlank(message = "O nome da turma é obrigatório!")
    private String nomeTurma;
    @Size(max = 255)
    private String descricaoTurma;
    @NotNull
    private Boolean statusTurma;
}
