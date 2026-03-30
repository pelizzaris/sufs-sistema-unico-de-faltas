package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.Auditoria;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.AuditoriaMapper;
import com.pelizzaris.sufs.repository.AuditoriaRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuditoriaMapper auditoriaMapper;

    @Transactional
    public void registrarAuditoria(String entidadeId, AcaoAuditoria acao) {
        var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID usuarioId = UUID.fromString(jwt.getSubject());

        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(usuarioRepository.getReferenceById(usuarioId));
        auditoria.setEntidadeId(entidadeId);
        auditoria.setAcaoRealizada(acao);
        auditoriaRepository.save(auditoria);
    }

    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> findAll() {
        return auditoriaRepository.findAll()
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> findByUsuarioId(UUID usuarioId) {
        return auditoriaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> findByAcaoRealizada(AcaoAuditoria acao) {
        return auditoriaRepository.findByAcaoRealizada(acao)
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
