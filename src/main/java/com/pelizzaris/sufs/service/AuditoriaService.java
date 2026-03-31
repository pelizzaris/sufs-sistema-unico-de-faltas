package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.Auditoria;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.AuditoriaMapper;
import com.pelizzaris.sufs.repository.AuditoriaRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Auditoria - auditoria registrada: {}", auditoria.getId());
    }

    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> findAll() {
        log.info("Auditoria - buscar todas");
        return auditoriaRepository.findAll()
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> findByUsuarioId(UUID usuarioId) {
        log.info("Auditoria - buscar pelo ID do usuário: {}", usuarioId);
        return auditoriaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> findByAcaoRealizada(AcaoAuditoria acao) {
        log.info("Auditoria - buscar pela ação realizada: {}", acao);
        return auditoriaRepository.findByAcaoRealizada(acao)
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
