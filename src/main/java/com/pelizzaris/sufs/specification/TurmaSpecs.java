package com.pelizzaris.sufs.specification;

import com.pelizzaris.sufs.domain.model.Turma;
import org.springframework.data.jpa.domain.Specification;

public class TurmaSpecs {

    public static Specification<Turma> buscarComNome(String nome) {
        return (root, query, cb) ->
                nome == null ? null : cb.like(cb.lower(root.get("nomeTurma")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Turma> buscarStatus(Boolean status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("statusTurma"), status);
    }
}