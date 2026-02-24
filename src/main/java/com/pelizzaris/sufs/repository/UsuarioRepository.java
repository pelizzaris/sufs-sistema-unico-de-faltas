package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

        List<Usuario> findByNomeContainingIgnoreCase(String nome);

        Optional<Usuario> findByEmail(String email);

        boolean existsByEmail(String email);

        List<Usuario> findByStatus(Boolean status);

        List<Usuario> findByRole(Roles role);
}
