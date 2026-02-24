package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.FaltaCreateDTO;
import com.pelizzaris.sufs.domain.dto.FaltaResponseDTO;
import com.pelizzaris.sufs.domain.dto.FaltaUpdateDTO;
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

import java.util.List;

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

    public void deletarFalta(Long id) {
        if (!faltaRepository.existsById(id)) {
            throw new RuntimeException("Falta não encontrada!");
        }
        faltaAlunoRepository.deleteByFaltaId(id);
        faltaRepository.deleteById(id);
    }
}
