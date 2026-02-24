package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    Optional<Aluno> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Aluno> findByStatus(Boolean status);

    //criar consulta para alunos com assuidade total
}