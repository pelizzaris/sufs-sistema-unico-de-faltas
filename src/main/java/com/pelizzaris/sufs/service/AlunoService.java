package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AlunoCreateDTO;
import com.pelizzaris.sufs.domain.dto.AlunoResponseDTO;
import com.pelizzaris.sufs.domain.dto.AlunoUpdateDTO;
import com.pelizzaris.sufs.domain.model.Aluno;
import com.pelizzaris.sufs.domain.model.Turma;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.AlunoMapper;
import com.pelizzaris.sufs.repository.AlunoRepository;
import com.pelizzaris.sufs.repository.TurmaRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import com.pelizzaris.sufs.specification.AlunoSpecs;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final AlunoMapper alunoMapper;
    private final AuditoriaService auditoriaService;
    private final UsuarioRepository usuarioRepository;

    public AlunoService(AlunoRepository alunoRepository, TurmaRepository turmaRepository, AlunoMapper alunoMapper, AuditoriaService auditoriaService, UsuarioRepository usuarioRepository) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.alunoMapper = alunoMapper;
        this.auditoriaService = auditoriaService;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public AlunoResponseDTO registrarAluno(AlunoCreateDTO dto) {
        var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID usuarioId = UUID.fromString(jwt.getSubject());

        if (alunoRepository.existsByEmail(dto.email())) {
            log.error("Aluno - aluno já cadastrado: {}", dto.email());
            throw new RuntimeException("Já existe um aluno cadastrado com este e-mail!");

        }

        Aluno aluno = alunoMapper.toEntity(dto);
        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> {
                    log.error("Aluno - Turma não encontrada com o ID: {}", dto.turmaId());
                    return new RuntimeException("Turma não encontrada com o ID: " + dto.turmaId());
                });

        aluno.setTurma(turma);
        aluno.setUsuarioId(usuarioRepository.getReferenceById(usuarioId));

        aluno = alunoRepository.save(aluno);
        log.info("Aluno - Aluno registrado com sucesso: {}", aluno.getId());
        auditoriaService.registrarAuditoria(String.valueOf(aluno.getId()), AcaoAuditoria.ALUNO_CRIADO);
        log.info("Aluno - Auditoria - Aluno registrado: {}", aluno.getId());
        return alunoMapper.toResponseDTO(aluno);
    }

    @Transactional
    public AlunoResponseDTO atualizarAluno(UUID id, AlunoUpdateDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Aluno - Aluno não encontrado com o ID: {}", id);
                    return new RuntimeException("Aluno não encontrado!");
                });

        if (!aluno.getStatus()) {
            log.error("Aluno - Alteração indisponível, aluno desativado: {}", id);
            throw new RuntimeException("Não é permitido atualizar dados de um aluno desativado!");
        }

        alunoMapper.updateEntityFromDTO(dto, aluno);

        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> {
                    log.error("Aluno - Turma não encontrada com o ID: {}", dto.turmaId());
                    return new RuntimeException("Turma não encontrada com o ID: " + dto.turmaId());
                });

        aluno.setTurma(turma);

        alunoRepository.save(aluno);
        log.info("Aluno - Aluno atualizado com sucesso: {}", aluno.getId());
        auditoriaService.registrarAuditoria(String.valueOf(aluno.getId()), AcaoAuditoria.ALUNO_ATUALIZADO);
        log.info("Aluno - Auditoria - Aluno atualizado: {}", aluno.getId());
        return alunoMapper.toResponseDTO(aluno);
    }

    @Transactional
    public void alterarStatus(UUID id, boolean status) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Aluno - Aluno não encontrado com o ID: {}", id);
                    return new RuntimeException("Aluno não encontrado!");
                });

        if(aluno.getStatus() == status) {
            String acao = status ? "ativado" : "desativado";
            log.error("Aluno - Status do usuário já consta como: {}", acao);
            throw new RuntimeException("O usuário já está " + acao + "!");
        }

        aluno.setStatus(status);
        alunoRepository.save(aluno);
        log.info("Aluno - Status atualizado com sucesso: {}", aluno.getId());
        AcaoAuditoria acao = status ? AcaoAuditoria.ALUNO_ATIVADO : AcaoAuditoria.ALUNO_DESATIVADO;
        auditoriaService.registrarAuditoria(String.valueOf(aluno.getId()), acao);
        log.info("Aluno - Auditoria - Status atualizado: {}", aluno.getId());
    }

    @Transactional
    public Page<AlunoResponseDTO> findAllSpecification(String nome, Boolean status, Pageable pageable) {
        log.info("Aluno - Buscar por nome: {}, status: {}, - Página: {}, Tamanho: {}", nome, status, pageable.getPageNumber(), pageable.getPageSize());
        Specification<Aluno> specs = Specification.allOf(
                AlunoSpecs.buscarComNome(nome),
                AlunoSpecs.buscarStatus(status)
        );

        return alunoRepository.findAll(specs, pageable)
                .map(alunoMapper::toResponseDTO);
    }

    @Transactional
    public AlunoResponseDTO findByEmail(String email) {
        log.info("Aluno - Buscar aluno pelo email: {}", email);
        return alunoRepository.findByEmail(email)
                .map(alunoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com este e-mail!"));
    }

    @Transactional
    public AlunoResponseDTO findById(UUID id) {
        log.info("Aluno - Buscar alunos pelo ID: {}", id);
        return alunoRepository.findById(id)
                .map(alunoMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com este ID!"));
    }
}
