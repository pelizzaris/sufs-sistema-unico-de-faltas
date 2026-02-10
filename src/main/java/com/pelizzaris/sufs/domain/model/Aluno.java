package com.pelizzaris.sufs.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_aluno", length = 150, nullable = false)
    private String nomeAluno;

    @Column(name = "status_aluno", nullable = false)
    private Boolean statusAluno;

    @Column(name = "turma_id", nullable = false)
    private Integer turmaId;

}
