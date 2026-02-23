package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    List<Auditoria> findByFaltaId(Integer faltaId);

    List<Auditoria> findByUsuarioId(UUID usuarioId);
}
