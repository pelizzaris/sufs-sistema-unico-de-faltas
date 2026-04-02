package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.domain.model.util.Roles;
import com.pelizzaris.sufs.mapper.UsuarioMapper;
import com.pelizzaris.sufs.repository.RolesRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import com.pelizzaris.sufs.specification.UsuarioSpecs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final AuditoriaService auditoriaService;
    private final RolesRepository rolesRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, AuditoriaService auditoriaService, RolesRepository rolesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.auditoriaService = auditoriaService;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioCreateDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            log.error("Usuário - Usuário já cadastrada com este email: {}", dto.email());
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail!");
        }

        Roles roleExistente = rolesRepository.findById(dto.role().getRoleId())
                .orElseThrow(() -> {
                    log.error("Usuário - Role não encontrada: {}", dto.role().getRoleId());
                    return new RuntimeException("Role não encontrada");
                });

        if(roleExistente.getNome().equalsIgnoreCase(Roles.Values.MASTER.name())) {
            log.error("Usuário - Proibido criar um usuário COM role: {}", Roles.Values.MASTER.name());
            throw new RuntimeException("Não é permitido criar um usuário com a role MASTER!");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(bCryptPasswordEncoder.encode(dto.senha()));
        usuario.setRole(Set.of(roleExistente));
        usuario = usuarioRepository.save(usuario);
        log.info("Usuário - Usuário registrado com sucesso: {}", usuario.getId());
        auditoriaService.registrarAuditoria(String.valueOf(usuario.getId()), AcaoAuditoria.USUARIO_CRIADO);
        log.info("Usuário - Auditoria - Usuário registrado: {}", usuario.getId());

        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(UUID id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuário - Usuário não encontrado: {}", id);
                    return new RuntimeException("Usuário não encontrado!");
                });

        if (!usuario.getStatus()) {
            log.error("Usuário - Não é permitido atualizar dados de um usuário desativado: {}" + id);
            throw new RuntimeException("Não é permitido atualizar dados de um usuário desativado!");
        }

        usuarioMapper.updateEntityFromDTO(dto, usuario);
        usuarioRepository.save(usuario);
        log.info("Usuário - Usuário atualizado com sucesso: {}", usuario.getId());
        auditoriaService.registrarAuditoria(String.valueOf(usuario.getId()), AcaoAuditoria.USUARIO_ATUALIZADO);
        log.info("Usuário - Auditoria - Usuário atualizado: {}", usuario.getId());
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public void alterarStatus(UUID id, boolean status) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuário - Usuário não encontrado: {}", id);
                    return new RuntimeException("Usuário não encontrado!");
                });

        if(usuario.getStatus() == status) {
            String acao = status ? "ativado" : "desativado";
            log.error("Usuário - Usuário já está: {}", acao);
            throw new RuntimeException("O usuário já está " + acao + "!");
        }

        usuario.setStatus(status);
        usuarioRepository.save(usuario);
        log.info("Usuário - Status atualizado com sucesso: {}", usuario.getId());
        AcaoAuditoria acao = status ? AcaoAuditoria.USUARIO_ATIVADO : AcaoAuditoria.USUARIO_DESATIVADO;
        log.info("Usuário - Auditoria - Status atualizado: {}", usuario.getId());
        auditoriaService.registrarAuditoria(String.valueOf(usuario.getId()), acao);
    }

    @Transactional
    public Page<UsuarioResponseDTO> findAllSpecification(String nome, Boolean status, Pageable pageable) {
        log.info("Usuário - Buscar por nome: {}, status: {}, - Página: {}, Tamanho: {}", nome, status, pageable.getPageNumber(), pageable.getPageSize());
        Specification<Usuario> specs = Specification.allOf(
                UsuarioSpecs.buscarComNome(nome),
                UsuarioSpecs.buscarStatus(status)
        );

        return usuarioRepository.findAll(specs, pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Transactional
    public UsuarioResponseDTO findByEmail(String email) {
        log.info("Usuário - Buscar por email: {}", email);
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este e-mail!"));
    }

    @Transactional
    public Optional<Usuario> findEntityByEmail(String email) {
        log.info("Usuário - Login - Buscar por email: {}", email);
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public UsuarioResponseDTO findById(UUID id) {
        log.info("Usuário - Buscar por ID: {}", id);
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este ID!"));
    }

    @Transactional
    public List<UsuarioResponseDTO> findByRoleUsuario(String role) {
        log.info("Usuário - Buscar por role: {}", role);
        return usuarioRepository.findByRole_NomeIgnoreCase(role)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
