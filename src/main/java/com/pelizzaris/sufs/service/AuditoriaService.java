package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.Auditoria;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.mapper.AuditoriaMapper;
import com.pelizzaris.sufs.repository.AuditoriaRepository;
import com.pelizzaris.sufs.repository.UsuarioRepository;
import com.pelizzaris.sufs.specification.AuditoriaSpecs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuditoriaMapper auditoriaMapper;

    @Transactional
    public void registrarAuditoria(String entidadeId, AcaoAuditoria acao) {
        var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID usuarioId = UUID.fromString(jwt.getSubject());

        Auditoria auditoria = new Auditoria();
        auditoria.setUsuario(usuarioRepository.getReferenceById(usuarioId));
        auditoria.setEntidadeId(entidadeId);
        auditoria.setAcaoRealizada(acao);
        auditoriaRepository.save(auditoria);
        log.info("Auditoria - auditoria registrada: {}", auditoria.getId());
    }

    @Transactional(readOnly = true)
    public Page<AuditoriaResponseDTO> findAllSpecification(
            LocalDateTime dataRegistro,
            AcaoAuditoria acaoRealizada,
            String entidadeId,
            UUID usuarioId,
            Pageable pageable
    ){
        Usuario usuarioRef = (usuarioId != null) ? usuarioRepository.getReferenceById(usuarioId) : null;

        Specification<Auditoria> spec = Specification.allOf(
                AuditoriaSpecs.buscarPelaData(dataRegistro),
                AuditoriaSpecs.buscarComAcaoRealizada(acaoRealizada),
                AuditoriaSpecs.buscarEntidade(entidadeId),
                AuditoriaSpecs.buscarPorUsuario(usuarioRef)
        );

        return  auditoriaRepository.findAll(spec, pageable)
                .map(auditoriaMapper::toResponseDTO);
    }
}
