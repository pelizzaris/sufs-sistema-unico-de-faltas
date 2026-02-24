package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.Auditoria;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.AuditoriaMapper;
import com.pelizzaris.sufs.repository.AuditoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final AuditoriaMapper auditoriaMapper;

    @Transactional
    public void registrarAuditoria(UUID usuarioId, Long faltaId, AcaoAuditoria acao) {
        Auditoria auditoria = new Auditoria();
        auditoria.setUsuarioId(usuarioId);
        auditoria.setFaltaId(faltaId.longValue());
        auditoria.setAcaoRealizada(acao);
        auditoriaRepository.save(auditoria);
    }

    public List<AuditoriaResponseDTO> listarAuditorias() {
        return auditoriaRepository.findAll()
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AuditoriaResponseDTO> listarAuditoriasPorUsuario(UUID usuarioId) {
        return auditoriaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AuditoriaResponseDTO> listarAuditoriasPorFalta(Long faltaId) {
        return auditoriaRepository.findByFaltaId(faltaId)
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AuditoriaResponseDTO> listarAuditoriasPorAcao(AcaoAuditoria acao) {
        return auditoriaRepository.findByAcaoRealizada(acao)
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
