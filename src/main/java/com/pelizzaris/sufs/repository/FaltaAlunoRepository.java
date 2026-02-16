package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.FaltaAluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FaltaAlunoRepository extends JpaRepository<FaltaAluno, Long> {

    List<FaltaAluno> findByAlunoId(UUID alunoId);

    List<FaltaAluno> findByAlunoIdAndFaltaDataFaltaBetween(UUID alunoId, LocalDate dataInicio, LocalDate dataFim);

    long countByAlunoIdAndFaltaDataFaltaBetween(UUID alunoId, LocalDate dataInicio, LocalDate dataFim);

    List<FaltaAluno> findByAlunoTurmaId(Integer turmaId);

    List<FaltaAluno> findByAlunoTurmaIdAndFaltaDataFaltaBetween(Integer turmaId, LocalDate inicio, LocalDate fim);
}
