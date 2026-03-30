package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.service.AuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<AuditoriaResponseDTO>> listarTudo() {
        return ResponseEntity.ok(auditoriaService.findAll());
    }

    @GetMapping(value = "/acao")
    @PreAuthorize("hasAuthority('SCOPE_MASTER')")
    public ResponseEntity<List<AuditoriaResponseDTO>> listarPorAcao(@RequestParam AcaoAuditoria acao) {
        return ResponseEntity.ok(auditoriaService.findByAcaoRealizada(acao));
    }

    @GetMapping(value = "/usuario/{usuarioId}")
    @PreAuthorize("hasAuthority('SCOPE_MASTER')")
    public ResponseEntity<List<AuditoriaResponseDTO>> buscarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(auditoriaService.findByUsuarioId(usuarioId));
    }
}
