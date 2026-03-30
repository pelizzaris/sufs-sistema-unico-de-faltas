package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.TurmaCreateDTO;
import com.pelizzaris.sufs.domain.dto.TurmaResponseDTO;
import com.pelizzaris.sufs.domain.dto.TurmaUpdateDTO;
import com.pelizzaris.sufs.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public TurmaResponseDTO registrarTurma(@RequestBody @Valid TurmaCreateDTO dto) {
        return turmaService.registrarTurma(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<TurmaResponseDTO> atualizarTurma(@PathVariable Long id, @RequestBody @Valid TurmaUpdateDTO dto) {
        return ResponseEntity.ok(turmaService.atualizarTurma(id, dto));
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        turmaService.alterarStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<Void> reativar(@PathVariable Long id) {
        turmaService.alterarStatus(id, true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<List<TurmaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(turmaService.findAll());
    }

    @GetMapping(value = "/nome")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<List<TurmaResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(turmaService.findByNomeTurmaContainingIgnoreCase(nome));
    }

    @GetMapping(value = "/status")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<List<TurmaResponseDTO>> buscarPorStatus(@RequestParam Boolean status) {
        return ResponseEntity.ok(turmaService.findByStatusTurma(status));
    }
}
