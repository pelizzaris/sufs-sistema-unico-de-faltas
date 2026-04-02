package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.service.AuditoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/auditorias")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_MASTER')")
    public ResponseEntity<Page<AuditoriaResponseDTO>> listarComFiltros(
            @RequestParam(required = false) LocalDateTime dataRegistro,
            @RequestParam(required = false) AcaoAuditoria acaoRealizada,
            @RequestParam(required = false) String entidadeId,
            @RequestParam(required = false) UUID usuarioId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(auditoriaService.findAllSpecification(dataRegistro, acaoRealizada, entidadeId, usuarioId, pageable));
    }
}
