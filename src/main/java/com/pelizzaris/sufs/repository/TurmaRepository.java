package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findByNomeTurmaContainingIgnoreCase(String nomeTurma);

    List<Turma> findByStatusTurma(Boolean statusTurma);

    boolean existsByNomeTurma(String nomeTurma);
}
