package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID>, JpaSpecificationExecutor<Aluno> {

    Optional<Aluno> findByEmail(String email);

    boolean existsByEmail(String email);
}