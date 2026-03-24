package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponseDTO registrarAluno(@RequestBody @Valid AlunoCreateDTO dto) {
        return alunoService.registrarAluno(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> atualizarAluno(@PathVariable UUID id, @RequestBody @Valid AlunoUpdateDTO dto) {
        return ResponseEntity.ok(alunoService.atualizarAluno(id, dto));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        alunoService.alterarStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> reativar(@PathVariable UUID id) {
        alunoService.alterarStatus(id, true);
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
