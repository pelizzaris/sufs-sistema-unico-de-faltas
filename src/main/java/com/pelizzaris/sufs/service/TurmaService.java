package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.TurmaCreateDTO;
import com.pelizzaris.sufs.domain.dto.TurmaResponseDTO;
import com.pelizzaris.sufs.domain.dto.TurmaUpdateDTO;
import com.pelizzaris.sufs.domain.model.Turma;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.TurmaMapper;
import com.pelizzaris.sufs.repository.TurmaRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import com.pelizzaris.sufs.specification.TurmaSpecs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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
            log.error("Turma - Turma já cadastrada: {}", dto.nomeTurma());
            throw new RuntimeException("Já existe uma turma cadastrada com este nome!");
        }

        Turma turma = turmaMapper.toEntity(dto);
        turma.setUsuario(usuarioRepository.getReferenceById(usuarioId));
        turma = turmaRepository.save(turma);
        log.info("Turma - Turma registrada com sucesso: {}", turma.getId());
        auditoriaService.registrarAuditoria(String.valueOf(turma.getId()), AcaoAuditoria.TURMA_REGISTRADA);
        log.info("Turma - Auditoria - Turma registrada: {}", turma.getId());
        return turmaMapper.toResponseDTO(turma);
    }

    @Transactional
    public TurmaResponseDTO atualizarTurma(Long id, TurmaUpdateDTO dto) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Turma - Turma com o nome: {}", dto.nomeTurma() + " - Não encontrada");
                    return new RuntimeException("Turma não encontrada!");
                });

        turmaMapper.updateEntityFromDTO(dto, turma);
        turmaRepository.save(turma);
        log.info("Turma - Turma atualizada com sucesso: {}", turma.getId());
        auditoriaService.registrarAuditoria(String.valueOf(turma.getId()), AcaoAuditoria.TURMA_ATUALIZADA);
        log.info("Turma - Auditoria - Turma atualizada: {}", turma.getId());
        return turmaMapper.toResponseDTO(turma);
    }

    @Transactional
    public void alterarStatus(Long id, boolean status) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Turma - Turma com o ID: {}", id, " - Não encontrada");
                    return new RuntimeException("Turma não encontrada!");
                });

        if(turma.getStatusTurma() == status) {
            String acao = status ? "ativado" : "desativado";
            log.error("Turma - Turma já está com status: {}", acao);
            throw new RuntimeException("Turma já está " + acao + "!");
        }

        turma.setStatusTurma(status);
        turmaRepository.save(turma);
        log.info("Turma - Turma atualizada com sucesso: {}", turma.getId());
        AcaoAuditoria acao = status ? AcaoAuditoria.TURMA_ATIVADA : AcaoAuditoria.TURMA_DESATIVADA;
        log.info("Turma - Auditoria - Status atualizado: {}", turma.getId());
        auditoriaService.registrarAuditoria(String.valueOf(turma.getId()), acao);
    }

    @Transactional
    public Page<TurmaResponseDTO> findAllSpecification(String nome, Boolean status, Pageable pageable) {
        log.info("Turma - Buscar por nome: {}, status: {}, - Página: {}, Tamanho: {}", nome, status, pageable.getPageNumber(), pageable.getPageSize());
        Specification<Turma> spec = Specification.allOf(
                TurmaSpecs.buscarComNome(nome),
                TurmaSpecs.buscarStatus(status)
                );

        return turmaRepository.findAll(spec, pageable)
                .map(turmaMapper::toResponseDTO);
    }
}
