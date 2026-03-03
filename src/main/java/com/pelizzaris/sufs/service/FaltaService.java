package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.FaltaCreateDTO;
import com.pelizzaris.sufs.domain.dto.FaltaResponseDTO;
import com.pelizzaris.sufs.domain.dto.FaltaUpdateDTO;
import com.pelizzaris.sufs.domain.dto.RelatorioFaltaAlunoResponseDTO;
import com.pelizzaris.sufs.domain.model.Aluno;
import com.pelizzaris.sufs.domain.model.Falta;
import com.pelizzaris.sufs.domain.model.FaltaAluno;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.FaltaMapper;
import com.pelizzaris.sufs.repository.AlunoRepository;
import com.pelizzaris.sufs.repository.FaltaAlunoRepository;
import com.pelizzaris.sufs.repository.FaltaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FaltaService {

    private final FaltaRepository faltaRepository;
    private final AlunoRepository alunoRepository;
    private final FaltaAlunoRepository faltaAlunoRepository;
    private final FaltaMapper faltaMapper;
    private final AuditoriaService auditoriaService;

    @Transactional
    public FaltaResponseDTO salvarFalta(FaltaCreateDTO dto) throws RuntimeException {
        List<Aluno> alunos = alunoRepository.findAllById(dto.alunosIds());

        if (alunos.isEmpty()) {
            throw new RuntimeException("Nenhum aluno selecionado para a falta.");
        }

        for (Aluno aluno : alunos) {
            if (!aluno.podeRegistrarFalta()) {
                throw new RuntimeException("Não é possível registrar falta para o aluno "
                        + aluno.getNome() + ". Verifique se o aluno e a turma estão ativos.");
            }
        }

        Falta falta = faltaMapper.toEntity(dto);

        for (Aluno aluno : alunos) {
            FaltaAluno faltaAluno = new FaltaAluno();
            faltaAluno.setFalta(falta);
            faltaAluno.setAluno(aluno);
            falta.getAlunosFaltosos().add(faltaAluno);
        }

        falta = faltaRepository.save(falta);
        auditoriaService.registrarAuditoria(dto.usuarioId(), falta.getId(), AcaoAuditoria.FALTA_REGISTRADA);
        return faltaMapper.toResponseDTO(falta);
    }

    @Transactional
    public FaltaResponseDTO atualizarFalta(Long id, FaltaUpdateDTO dto) {
        Falta falta = faltaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Falta não encontrada!"));

        List<Aluno> alunos = alunoRepository.findAllById(dto.alunosIds());

        if (alunos.isEmpty()) {
            throw new RuntimeException("Nenhum aluno selecionado para atualizar a falta.");
        }

        for (Aluno aluno : alunos) {
            if (!aluno.podeRegistrarFalta()) {
                throw new RuntimeException("Não é possível registrar falta para o aluno "
                        + aluno.getNome() + ". Verifique se o aluno e a turma estão ativos.");
            }
        }

        faltaMapper.updateEntityFromDTO(dto, falta);
        faltaAlunoRepository.deleteByFaltaId(id);

        for (Aluno aluno : alunos) {
            FaltaAluno faltaAluno = new FaltaAluno();
            faltaAluno.setFalta(falta);
            faltaAluno.setAluno(aluno);
            falta.getAlunosFaltosos().add(faltaAluno);
        }

        falta = faltaRepository.save(falta);
        auditoriaService.registrarAuditoria(dto.usuarioId(), falta.getId(), AcaoAuditoria.FALTA_ATUALIZADA);
        return faltaMapper.toResponseDTO(falta);
    }

    @Transactional
    public void deletarFalta(Long id) {
        if (!faltaRepository.existsById(id)) {
            throw new RuntimeException("Falta não encontrada!");
        }
        faltaAlunoRepository.deleteByFaltaId(id);
        faltaRepository.deleteById(id);
    }

    public List<FaltaResponseDTO> findAll() {
        return faltaRepository.findAll()
                .stream()
                .map(faltaMapper::toResponseDTO)
                .toList();
    }

    public List<FaltaResponseDTO> findByDataRegistro(LocalDate dataRegistro) {
        return faltaRepository.findByDataRegistro(dataRegistro)
                .stream()
                .map(faltaMapper::toResponseDTO)
                .toList();
    }

    public List<FaltaResponseDTO> findByUsuarioId(UUID usuarioId) {
        return faltaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(faltaMapper::toResponseDTO)
                .toList();
    }

    public List<RelatorioFaltaAlunoResponseDTO> findByAlunoId(UUID alunoId) {
        return faltaAlunoRepository.findByAlunoId(alunoId)
                .stream()
                .map(faltaMapper::relatorioFalta)
                .toList();
    }

    public List<FaltaResponseDTO> findByAlunoIdAndDataFaltaBetween(UUID alunoId, LocalDate dataInicio, LocalDate dataFim) {
        return faltaAlunoRepository.findByAlunoIdAndFaltaDataFaltaBetween(alunoId, dataInicio, dataFim)
                .stream()
                .map(faltaAluno -> faltaMapper.toResponseDTO(faltaAluno.getFalta()))
                .toList();
    }

     public List<FaltaResponseDTO> findByTurmaId(Long turmaId) {
        return faltaAlunoRepository.findByAlunoTurmaId(turmaId)
                .stream()
                .map(faltaAluno -> faltaMapper.toResponseDTO(faltaAluno.getFalta()))
                .toList();
    }

    public List<FaltaResponseDTO> findByTurmaIdAndDataFaltaBetween(Long turmaId, LocalDate dataInicio, LocalDate dataFim) {
        return faltaAlunoRepository.findByAlunoTurmaIdAndFaltaDataFaltaBetween(turmaId, dataInicio, dataFim)
                .stream()
                .map(faltaAluno -> faltaMapper.toResponseDTO(faltaAluno.getFalta()))
                .toList();
    }
}
