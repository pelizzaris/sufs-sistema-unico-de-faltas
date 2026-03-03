package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.service.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/auditorias")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    public ResponseEntity<List<AuditoriaResponseDTO>> listarTudo() {
        return ResponseEntity.ok(auditoriaService.findAll());
    }

    @GetMapping(value = "/acao")
    public ResponseEntity<List<AuditoriaResponseDTO>> listarPorAcao(@RequestParam AcaoAuditoria acao) {
        return ResponseEntity.ok(auditoriaService.findByAcaoRealizada(acao));
    }

    @GetMapping(value = "/usuario/{usuarioId}")
    public ResponseEntity<List<AuditoriaResponseDTO>> buscarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(auditoriaService.findByUsuarioId(usuarioId));
    }

    @GetMapping(value = "/falta/{faltaId}")
    public ResponseEntity<List<AuditoriaResponseDTO>> buscarPorFalta(@PathVariable Long faltaId) {
        return ResponseEntity.ok(auditoriaService.findByFaltaId(faltaId));
    }
}
