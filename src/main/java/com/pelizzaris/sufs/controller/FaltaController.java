package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.service.FaltaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/faltas")
public class FaltaController {

    private final FaltaService faltaService;

    public FaltaController(FaltaService faltaService) {
        this.faltaService = faltaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public FaltaResponseDTO registrarFalta(@RequestBody @Valid FaltaCreateDTO dto) {
        return faltaService.registrarFalta(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<FaltaResponseDTO> atualizarFalta(@PathVariable Long id, @RequestBody @Valid FaltaUpdateDTO dto) {
        return ResponseEntity.ok(faltaService.atualizarFalta(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<Void> deletarFalta(@PathVariable Long id) {
        faltaService.deletarFalta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<FaltaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(faltaService.findAll());
    }

    @GetMapping("/relatorio/data")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<FaltaResponseDTO>> buscarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFalta) {

        return ResponseEntity.ok(faltaService.findByDataFalta(dataFalta));
    }

    @GetMapping(value = "/relatorio/usuario/{usuarioId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<List<FaltaResponseDTO>> buscarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(faltaService.findByUsuarioId(usuarioId));
    }

    @GetMapping(value = "/relatorio/aluno/{alunoId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<RelatorioFaltaAlunoResponseDTO>> buscarPorAluno(@PathVariable UUID alunoId) {
        return ResponseEntity.ok(faltaService.findByAlunoId(alunoId));
    }

    @GetMapping("/relatorio/aluno/{alunoId}/periodo")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<?>> buscarAlunoPorPeriodo(
            @PathVariable UUID alunoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        if (dataInicio != null && dataFim != null) {
            return ResponseEntity.ok(faltaService.findByAlunoIdAndDataFaltaBetween(alunoId, dataInicio, dataFim));
        }
        return ResponseEntity.ok(faltaService.findByAlunoId(alunoId));
    }

    @GetMapping(value = "/relatorio/turma/{turmaId}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<FaltaResponseDTO>> buscarPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(faltaService.findByTurmaId(turmaId));
    }

    @GetMapping("/relatorio/turma/{turmaId}/periodo")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<FaltaResponseDTO>> buscarTurmaPorPeriodo(
            @PathVariable Long turmaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        if (dataInicio != null && dataFim != null) {
            return ResponseEntity.ok(faltaService.findByTurmaIdAndDataFaltaBetween(turmaId, dataInicio, dataFim));
        }
        return ResponseEntity.ok(faltaService.findByTurmaId(turmaId));
    }
}
