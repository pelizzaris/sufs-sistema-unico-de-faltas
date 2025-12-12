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
    @Column(name = "nome_turma", unique = true, length = 150, nullable = false)
    private String nomeTurma;
    @Column(name = "descricao_turma", length = 255, nullable = false)
    private String descricaoTurma;
    @Column(name = "status_turma", nullable = false)
    private Boolean statusTurma;
}
