package com.pelizzaris.sufs.specification;

import com.pelizzaris.sufs.domain.model.Aluno;
import org.springframework.data.jpa.domain.Specification;

public class AlunoSpecs {

    public static Specification<Aluno> buscarComNome(String nome) {
        return (root, query, cb) ->
                nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Aluno> buscarStatus(Boolean status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
