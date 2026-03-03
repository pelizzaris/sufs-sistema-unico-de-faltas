package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AlunoCreateDTO;
import com.pelizzaris.sufs.domain.dto.AlunoResponseDTO;
import com.pelizzaris.sufs.domain.dto.AlunoUpdateDTO;
import com.pelizzaris.sufs.domain.model.Aluno;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.AlunoMapper;
import com.pelizzaris.sufs.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;
    private final AuditoriaService auditoriaService;

    @Transactional
    public AlunoResponseDTO salvarAluno(AlunoCreateDTO dto) {

        if (alunoRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Já existe um aluno cadastrado com este e-mail!");
        }

        Aluno aluno = alunoMapper.toEntity(dto);
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toResponseDTO(aluno);
    }

    @Transactional
    public AlunoResponseDTO atualizarAluno(UUID id, AlunoUpdateDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        if (!aluno.getStatus()) {
            throw new RuntimeException("Não é permitido atualizar dados de um aluno desativado!");
        }

        alunoMapper.updateEntityFromDTO(dto, aluno);
        alunoRepository.save(aluno);
        return alunoMapper.toResponseDTO(aluno);
    }

    @Transactional
    public void deletarAluno(UUID id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        if (!aluno.getStatus()) {
            throw new RuntimeException("Este aluno já está desativado!");
        }

        aluno.setStatus(false);
        alunoRepository.save(aluno);
        auditoriaService.registrarAuditoria(id, null, AcaoAuditoria.PESSOA_DESATIVADA);
    }

    public List<AlunoResponseDTO> findAll() {
        return alunoRepository.findAll()
            .stream()
            .map(alunoMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public List<AlunoResponseDTO> findByNomeAlunoContainingIgnoreCase(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(alunoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AlunoResponseDTO findByEmailAluno(String email) {
        return alunoRepository.findByEmail(email)
                .map(alunoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com este e-mail!"));
    }

    public AlunoResponseDTO findByIdAluno(UUID id) {
        return alunoRepository.findById(id)
                .map(alunoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com este ID!"));
    }

    public List<AlunoResponseDTO> findByStatusAluno(Boolean status) {
        return alunoRepository.findByStatus(status)
                .stream()
                .map(alunoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
