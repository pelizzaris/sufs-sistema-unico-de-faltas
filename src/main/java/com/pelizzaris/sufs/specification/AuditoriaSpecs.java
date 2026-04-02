package com.pelizzaris.sufs.specification;

import com.pelizzaris.sufs.domain.model.Auditoria;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class AuditoriaSpecs {

    public static Specification<Auditoria> buscarPelaData(LocalDateTime dataRegistro) {
        return (root, query, cb) ->
                dataRegistro == null ? null : cb.equal(root.get("dataRegistro"), dataRegistro);
    }

    public static Specification<Auditoria> buscarComAcaoRealizada(AcaoAuditoria acaoRealizada) {
        return (root, query, cb) ->
                acaoRealizada == null ? null : cb.equal(root.get("acaoRealizada"), acaoRealizada);
    }

    public static Specification<Auditoria> buscarEntidade(String entidadeId) {
        return (root, query, cb) ->
                entidadeId == null ? null : cb.equal(root.get("entidadeId"), entidadeId);
    }

    public static Specification<Auditoria> buscarPorUsuario(Usuario usuario) {
        return (root, query, cb) ->
                usuario == null ? null : cb.equal(root.get("usuario"), usuario);
    }
}
