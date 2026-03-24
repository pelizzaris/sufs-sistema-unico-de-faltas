package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.util.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Roles findByNome(String nome);
}
