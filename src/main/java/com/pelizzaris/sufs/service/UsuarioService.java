package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.UsuarioCreateDTO;
import com.pelizzaris.sufs.domain.dto.UsuarioResponseDTO;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.mapper.UsuarioMapper;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponseDTO salvarUsuario(UsuarioCreateDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuario);
    }

    public void deletarUsuario(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        usuarioRepository.deleteById(id);
    }
}
