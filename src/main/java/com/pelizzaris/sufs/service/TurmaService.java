package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.TurmaCreateDTO;
import com.pelizzaris.sufs.domain.dto.TurmaResponseDTO;
import com.pelizzaris.sufs.domain.dto.TurmaUpdateDTO;
import com.pelizzaris.sufs.domain.model.Turma;
import com.pelizzaris.sufs.mapper.TurmaMapper;
import com.pelizzaris.sufs.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;
    private final AuditoriaService auditoriaService;

    @Transactional
    public TurmaResponseDTO registrarTurma(TurmaCreateDTO dto) {
        if (turmaRepository.existsByNomeTurma(dto.nomeTurma())) {
            throw new RuntimeException("Já existe uma turma cadastrada com este nome!");
        }

        Turma turma = turmaMapper.toEntity(dto);
        turma = turmaRepository.save(turma);
        return turmaMapper.toResponseDTO(turma);
        //auditoriaService.registrarAuditoria(id, null, AcaoAuditoria.TURMA_REGISTRADA);
    }

    @Transactional
    public TurmaResponseDTO atualizarTurma(Long id, TurmaUpdateDTO dto) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));
        turmaMapper.updateEntityFromDTO(dto, turma);
        turmaRepository.save(turma);
        return turmaMapper.toResponseDTO(turma);

        //auditoriaService.registrarAuditoria(id, null, AcaoAuditoria.TURMA_ATUALIZADA);
    }

    @Transactional
    public void deletarTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada!"));

        if (!turmaRepository.existsById(id)) {
            throw new RuntimeException("Turma não encontrada!");
        }
        if (!turma.getStatusTurma()) {
            throw new RuntimeException("Esta turma já está desativada!");
        }

        turma.setStatusTurma(false);
        turmaRepository.save(turma);

        //auditoriaService.registrarAuditoria(id, null, AcaoAuditoria.TURMA_DELETADA);
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
