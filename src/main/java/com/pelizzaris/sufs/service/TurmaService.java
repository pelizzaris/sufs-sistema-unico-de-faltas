package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.TurmaCreateDTO;
import com.pelizzaris.sufs.domain.dto.TurmaResponseDTO;
import com.pelizzaris.sufs.domain.dto.TurmaUpdateDTO;
import com.pelizzaris.sufs.domain.model.Turma;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.TurmaMapper;
import com.pelizzaris.sufs.repository.TurmaRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;
    private final AuditoriaService auditoriaService;
    private final UsuarioRepository usuarioRepository;

    public TurmaService(TurmaRepository turmaRepository, TurmaMapper turmaMapper, AuditoriaService auditoriaService, UsuarioRepository usuarioRepository) {
        this.turmaRepository = turmaRepository;
        this.turmaMapper = turmaMapper;
        this.auditoriaService = auditoriaService;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public TurmaResponseDTO registrarTurma(TurmaCreateDTO dto) {
        var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID usuarioId = UUID.fromString(jwt.getSubject());

        if (turmaRepository.existsByNomeTurma(dto.nomeTurma())) {
            throw new RuntimeException("Já existe uma turma cadastrada com este nome!");
        }

        Turma turma = turmaMapper.toEntity(dto);
        turma.setUsuario(usuarioRepository.getReferenceById(usuarioId));
        turma = turmaRepository.save(turma);
        auditoriaService.registrarAuditoria(String.valueOf(turma.getId()), AcaoAuditoria.TURMA_REGISTRADA);
        return turmaMapper.toResponseDTO(turma);
    }

    @Transactional
    public TurmaResponseDTO atualizarTurma(Long id, TurmaUpdateDTO dto) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));
        turmaMapper.updateEntityFromDTO(dto, turma);
        turmaRepository.save(turma);
        auditoriaService.registrarAuditoria(String.valueOf(turma.getId()), AcaoAuditoria.TURMA_ATUALIZADA);
        return turmaMapper.toResponseDTO(turma);
    }

    @Transactional
    public void alterarStatus(Long id, boolean status) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));

        if(turma.getStatusTurma() == status) {
            String acao = status ? "ativado" : "desativado";
            throw new RuntimeException("Turma já está " + acao + "!");
        }

        turma.setStatusTurma(status);
        turmaRepository.save(turma);
        AcaoAuditoria acao = status ? AcaoAuditoria.TURMA_ATIVADA : AcaoAuditoria.TURMA_DESATIVADA;
        auditoriaService.registrarAuditoria(String.valueOf(turma.getId()), acao);
    }

    public List<TurmaResponseDTO> findAll() {
        return turmaRepository.findAll()
                .stream()
                .map(turmaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TurmaResponseDTO> findByNomeTurmaContainingIgnoreCase(String nome) {
        return turmaRepository.findByNomeTurmaContainingIgnoreCase(nome)
                .stream()
                .map(turmaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<TurmaResponseDTO> findByStatusTurma(Boolean status) {
        return turmaRepository.findByStatusTurma(status)
                .stream()
                .map(turmaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
