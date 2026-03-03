package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.dto.TurmaCreateDTO;
import com.pelizzaris.sufs.domain.dto.TurmaResponseDTO;
import com.pelizzaris.sufs.domain.dto.TurmaUpdateDTO;
import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
import com.pelizzaris.sufs.service.TurmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/turmas")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService turmaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurmaResponseDTO create(@RequestBody @Valid TurmaCreateDTO dto) {
        return turmaService.registrarTurma(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid TurmaUpdateDTO dto) {
        return ResponseEntity.ok(turmaService.atualizarTurma(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TurmaResponseDTO>> getAll() {
        return ResponseEntity.ok(turmaService.findAll());
    }

    @GetMapping(value = "/nome/{nome}")
    public ResponseEntity<List<TurmaResponseDTO>> findByNomeTurmaContainingIgnoreCase(String nome) {
        return ResponseEntity.ok(turmaService.findByNomeTurmaContainingIgnoreCase(nome));
    }

    @GetMapping(value = "/status/{status}")
    public ResponseEntity<List<TurmaResponseDTO>> findByStatusTurma(Boolean status) {
        return ResponseEntity.ok(turmaService.findByStatusTurma(status));
    }
}
