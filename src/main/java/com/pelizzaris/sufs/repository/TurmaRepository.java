package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    Optional<Turma> findByNomeTurmaContainingIgnoreCase(String nomeTurma);

    boolean existsByNomeTurma(String nomeTurma);
}
