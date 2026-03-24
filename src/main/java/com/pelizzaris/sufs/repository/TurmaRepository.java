package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findByNomeTurmaContainingIgnoreCase(String nomeTurma);

    List<Turma> findByStatusTurma(Boolean statusTurma);

    boolean existsByNomeTurma(String nomeTurma);
}
