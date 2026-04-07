package com.pelizzaris.sufs.config;

import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.domain.model.util.Roles;
import com.pelizzaris.sufs.repository.RolesRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import com.pelizzaris.sufs.service.AuditoriaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Configuration
@Slf4j
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

        var usuarioAdmin = usuarioRepository.findByNome("a@a");

        usuarioAdmin.ifPresentOrElse(
                usuario -> {
                    log.info("Usuário MASTER já existe: {}", usuario.getNome());
                },
                () -> {
                    var master = new Usuario();
                    master.setNome("a@a");
                    master.setEmail("a@a");
                    master.setSenha(bCryptPasswordEncoder.encode("a@a"));

                    if (roleAdmin != null) {
                        Set<Roles> roles = new HashSet<>();
                        roles.add(roleAdmin);
                        master.setRoles(roles);
                    }

                    master = usuarioRepository.saveAndFlush(master);

                    master.setUsuarioCriador(master);
                    usuarioRepository.save(master);
                    log.info("Usuário MASTER criado com sucesso: {}", master.getNome());
                }
        );
    }
}