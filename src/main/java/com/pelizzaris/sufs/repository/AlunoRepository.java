package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

    List<Aluno> findAll();
}
