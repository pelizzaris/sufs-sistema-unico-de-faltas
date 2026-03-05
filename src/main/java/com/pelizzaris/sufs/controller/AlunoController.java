package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponseDTO registrarAluno(@RequestBody @Valid AlunoCreateDTO dto) {
        return alunoService.registrarAluno(dto);
    }

    @PutMapping("/{alunoId}")
    public ResponseEntity<AlunoResponseDTO> atualizarAluno(@PathVariable UUID alunoId, @RequestBody @Valid AlunoUpdateDTO dto) {
        return ResponseEntity.ok(alunoService.atualizarAluno(alunoId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable UUID id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(alunoService.findAll());
    }

    @GetMapping(value = "/nome")
    public ResponseEntity<List<AlunoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(alunoService.findByNomeAlunoContainingIgnoreCase(nome));
    }

    @GetMapping(value = "/email")
    public ResponseEntity<AlunoResponseDTO> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(alunoService.findByEmailAluno(email));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(alunoService.findByIdAluno(id));
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<AlunoResponseDTO>> buscarPorStatus(@RequestParam Boolean status) {
        return ResponseEntity.ok(alunoService.findByStatusAluno(status));
    }
}
