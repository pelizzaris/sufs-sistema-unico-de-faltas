package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AlunoCreateDTO;
import com.pelizzaris.sufs.domain.dto.AlunoResponseDTO;
import com.pelizzaris.sufs.domain.dto.AlunoUpdateDTO;
import com.pelizzaris.sufs.domain.model.Aluno;
import com.pelizzaris.sufs.domain.model.Turma;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.AlunoMapper;
import com.pelizzaris.sufs.repository.AlunoRepository;
import com.pelizzaris.sufs.repository.TurmaRepository;
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
    private final TurmaRepository turmaRepository;
    private final AlunoMapper alunoMapper;
    private final AuditoriaService auditoriaService;

    @Transactional
    public AlunoResponseDTO registrarAluno(AlunoCreateDTO dto) {

        if (alunoRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Já existe um aluno cadastrado com este e-mail!");
        }

        Aluno aluno = alunoMapper.toEntity(dto);
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com o ID: " + dto.turmaId()));

        aluno.setTurma(turma);

        aluno = alunoRepository.save(aluno);

        //auditoriaService.registrarAuditoria(id, null, AcaoAuditoria.ALUNO_CRIADO);
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

        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada com o ID: " + dto.turmaId()));

        aluno.setTurma(turma);

        alunoRepository.save(aluno);

        //auditoriaService.registrarAuditoria(id, null, AcaoAuditoria.ALUNO_ATUALIZADO);
        return alunoMapper.toResponseDTO(aluno);
    }

    @Transactional
    public void alterarStatus(UUID id, boolean status) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));

        if(aluno.getStatus() == status) {
            String acao = status ? "ativado" : "desativado";
            throw new RuntimeException("O usuário já está " + acao + "!");
        }

        aluno.setStatus(status);
        alunoRepository.save(aluno);
        //AcaoAuditoria acao = status ? AcaoAuditoria.USUARIO_ATIVADO : AcaoAuditoria.USUARIO_DESATIVADO;
        //auditoriaService.registrarAuditoria(usuarioLogadoId, id, acao);
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
