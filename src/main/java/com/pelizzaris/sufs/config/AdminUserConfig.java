package com.pelizzaris.sufs.config;

import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.domain.model.util.Roles;
import com.pelizzaris.sufs.repository.RolesRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import com.pelizzaris.sufs.service.AuditoriaService;
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
    private AuditoriaService auditoriaService;

    public AdminUserConfig(RolesRepository rolesRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuditoriaService auditoriaService) {
        this.rolesRepository = rolesRepository;
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.auditoriaService = auditoriaService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = rolesRepository.findByNome(Roles.Values.MASTER.name());

        var usuarioAdmin = usuarioRepository.findByNome("a@a");

        usuarioAdmin.ifPresentOrElse(
                usuario -> {
                    System.out.println("\nUsuário MASTER já existe.");
                },
                () -> {
                    var usuario = new Usuario();
                    usuario.setNome("a@a");
                    usuario.setEmail("a@a");
                    usuario.setSenha(bCryptPasswordEncoder.encode("a@a"));
                    usuario.setRoles(Set.of(roleAdmin));
                    usuarioRepository.save(usuario);
                    System.out.println("\nUsuário MASTER criado com sucesso.\n");
                }
        );
    }
}