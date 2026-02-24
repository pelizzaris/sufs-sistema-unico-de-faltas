package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.TurmaCreateDTO;
import com.pelizzaris.sufs.domain.dto.TurmaResponseDTO;
import com.pelizzaris.sufs.domain.model.Turma;
import com.pelizzaris.sufs.mapper.TurmaMapper;
import com.pelizzaris.sufs.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;

    public TurmaResponseDTO salvarTurma(TurmaCreateDTO dto) {
        Turma turma = turmaMapper.toEntity(dto);
        turma = turmaRepository.save(turma);
        return turmaMapper.toResponseDTO(turma);
    }

    public void deletarTurma(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new RuntimeException("Turma não encontrada!");
        }
        turmaRepository.deleteById(id);
    }
}
