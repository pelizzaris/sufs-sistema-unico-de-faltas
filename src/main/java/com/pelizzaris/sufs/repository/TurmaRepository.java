package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long>, JpaSpecificationExecutor<Turma> {

    boolean existsByNomeTurma(String nomeTurma);
}
