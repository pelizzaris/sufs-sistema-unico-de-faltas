package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

        List<Usuario> findByNomeUsuarioContainingIgnoreCase(String nomeUsuario);

        Optional<Usuario> findByEmailUsuario(String emailUsuario);

        List<Usuario> findByStatusUsuario(Boolean statusUsuario);

        //List<Usuario> findByRoles(Roles roles);
}
