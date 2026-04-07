package com.pelizzaris.sufs.specification;

import com.pelizzaris.sufs.domain.model.Falta;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class FaltaSpecs {

    // Filtro por Usuário (Professor que registrou)
    public static Specification<Falta> porUsuario(UUID usuarioId) {
        return (root, query, cb) ->
                usuarioId == null ? null : cb.equal(root.get("usuario").get("id"), usuarioId);
    }

    // Filtro por Turma (Através dos alunos daquela falta)
    public static Specification<Falta> porTurma(Long turmaId) {
        return (root, query, cb) -> {
            if (turmaId == null) return null;
            // JOIN: Falta -> AlunosFaltosos -> Aluno -> Turma
            return cb.equal(root.join("alunosFaltosos").join("aluno").get("turma").get("id"), turmaId);
        };
    }

    // Filtro por Aluno Específico
    public static Specification<Falta> porAluno(UUID alunoId) {
        return (root, query, cb) -> {
            if (alunoId == null) return null;
            // JOIN: Falta -> AlunosFaltosos -> Aluno
            return cb.equal(root.join("alunosFaltosos").join("aluno").get("id"), alunoId);
        };
    }

    // Filtro por Período (O "Between" que você pediu)
    public static Specification<Falta> noPeriodo(LocalDate inicio, LocalDate fim) {
        return (root, query, cb) -> {
            if (inicio == null && fim == null) return null;
            if (inicio != null && fim == null) return cb.greaterThanOrEqualTo(root.get("dataFalta"), inicio);
            if (inicio == null && fim != null) return cb.lessThanOrEqualTo(root.get("dataFalta"), fim);
            return cb.between(root.get("dataFalta"), inicio, fim);
        };
    }
}
