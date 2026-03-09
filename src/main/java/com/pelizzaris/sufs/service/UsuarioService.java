package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.domain.model.util.Roles;
import com.pelizzaris.sufs.mapper.UsuarioMapper;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final AuditoriaService auditoriaService;

    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioCreateDTO dto) {

        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Já existe um aluno cadastrado com este e-mail!");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario = usuarioRepository.save(usuario);
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
        //AcaoAuditoria acao = status ? AcaoAuditoria.USUARIO_ATIVADO : AcaoAuditoria.USUARIO_DESATIVADO;
        //auditoriaService.registrarAuditoria(usuarioLogadoId, id, acao);
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

    public UsuarioResponseDTO findByEmailUsuario(String email) {
        return usuarioRepository.findByEmail(email)
                .map(usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este e-mail!"));
    }

    public UsuarioResponseDTO findById(UUID id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com este ID!"));
    }

    public List<UsuarioResponseDTO> findByStatusUsuario(Boolean status) {
        return usuarioRepository.findByStatus(status)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> findByRoleUsuario(Roles role) {
        return usuarioRepository.findByRole(role)
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
