package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.domain.model.util.Roles;
import com.pelizzaris.sufs.mapper.UsuarioMapper;
import com.pelizzaris.sufs.repository.RolesRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
            throw new RuntimeException("Já existe um usuário cadastrado com este e-mail!");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);

        usuario.setSenha(bCryptPasswordEncoder.encode(dto.senha()));

        Roles roleExistente = rolesRepository.findById(dto.role().getRoleId())
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        usuario.setRole(Set.of(roleExistente));
        usuario = usuarioRepository.save(usuario);
        auditoriaService.registrarAuditoria(String.valueOf(usuario.getId()), AcaoAuditoria.USUARIO_CRIADO);
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(UUID id, UsuarioUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!usuario.getStatus()) {
            throw new RuntimeException("Não é permitido atualizar dados de um usuário desativado!");
        }

        usuarioMapper.updateEntityFromDTO(dto, usuario);
        usuarioRepository.save(usuario);
        auditoriaService.registrarAuditoria(String.valueOf(usuario.getId()), AcaoAuditoria.USUARIO_ATUALIZADO);
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional
    public void alterarStatus(UUID id, boolean status) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if(usuario.getStatus() == status) {
            String acao = status ? "ativado" : "desativado";
            throw new RuntimeException("O usuário já está " + acao + "!");
        }

        usuario.setStatus(status);
        usuarioRepository.save(usuario);
        AcaoAuditoria acao = status ? AcaoAuditoria.USUARIO_ATIVADO : AcaoAuditoria.USUARIO_DESATIVADO;
        auditoriaService.registrarAuditoria(String.valueOf(usuario.getId()), acao);
    }

    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    public List<UsuarioResponseDTO> findByNomeContainingIgnoreCase(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este e-mail!"));
    }

    public Optional<Usuario> findEntityByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioResponseDTO findById(UUID id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este ID!"));
    }

    public List<UsuarioResponseDTO> findByStatus(Boolean status) {
        return usuarioRepository.findByStatus(status)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> findByRoleUsuario(String role) {
        return usuarioRepository.findByRole_NomeIgnoreCase(role)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
