package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public AlunoResponseDTO registrarAluno(@RequestBody @Valid AlunoCreateDTO dto) {
        return alunoService.registrarAluno(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<AlunoResponseDTO> atualizarAluno(@PathVariable UUID id, @RequestBody @Valid AlunoUpdateDTO dto) {
        return ResponseEntity.ok(alunoService.atualizarAluno(id, dto));
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        alunoService.alterarStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<Void> reativar(@PathVariable UUID id) {
        alunoService.alterarStatus(id, true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<List<AlunoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(alunoService.findAll());
    }

    @GetMapping(value = "/nome")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<List<AlunoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(alunoService.findByNomeAlunoContainingIgnoreCase(nome));
    }

    @GetMapping(value = "/email")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<AlunoResponseDTO> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(alunoService.findByEmail(email));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(alunoService.findById(id));
    }

    @GetMapping(value = "/status")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN', 'SCOPE_USUARIO')")
    public ResponseEntity<List<AlunoResponseDTO>> buscarPorStatus(@RequestParam Boolean status) {
        return ResponseEntity.ok(alunoService.findByStatus(status));
    }
}
