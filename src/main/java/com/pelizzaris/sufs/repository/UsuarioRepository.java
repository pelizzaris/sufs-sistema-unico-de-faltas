package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

        List<Usuario> findByNomeContainingIgnoreCase(String nome);
        Optional<Usuario> findByNome(String nome);

        Optional<Usuario> findByEmail(String email);

        boolean existsByEmail(String email);

        List<Usuario> findByStatus(Boolean status);

        List<Usuario> findByRole_NomeIgnoreCase(String role);
}
