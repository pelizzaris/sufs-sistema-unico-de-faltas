package com.pelizzaris.sufs.specification;

import com.pelizzaris.sufs.domain.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecs {

    public static Specification<Usuario> buscarComNome(String nome) {
        return (root, query, cb) ->
                nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Usuario> buscarStatus(Boolean status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }
}
