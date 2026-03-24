package com.pelizzaris.sufs.config;

import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.Roles;
import com.pelizzaris.sufs.repository.RolesRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RolesRepository rolesRepository;
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RolesRepository rolesRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.rolesRepository = rolesRepository;
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = rolesRepository.findByNome(Roles.Values.MASTER.name());

        var usuarioAdmin = usuarioRepository.findByNome("master");

        usuarioAdmin.ifPresentOrElse(
                usuario -> {
                    System.out.println("\nUsuário MASTER já existe.");
                },
                () -> {
                    var usuario = new Usuario();
                    usuario.setNome("master");
                    usuario.setEmail("master.pelizzaris@pelizzaris.com");
                    usuario.setSenha(bCryptPasswordEncoder.encode("admin123"));
                    usuario.setRoles(Set.of(roleAdmin));
                    usuarioRepository.save(usuario);
                    System.out.println("\nUsuário MASTER criado com sucesso.\n");
                }
        );
    }
}