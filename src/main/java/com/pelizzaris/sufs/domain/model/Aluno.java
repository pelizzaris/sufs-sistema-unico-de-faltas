package com.pelizzaris.sufs.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_aluno")
public class Aluno extends Pessoa {

    @ManyToOne(optional = false)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;

    public boolean podeRegistrarFalta() {
        return this.getStatus() && this.turma.getStatusTurma();
    }
}
