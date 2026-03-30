package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public UsuarioResponseDTO registrarUsuario(@RequestBody @Valid UsuarioCreateDTO dto) {
        return usuarioService.registrarUsuario(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        usuarioService.alterarStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<Void> reativar(@PathVariable UUID id) {
        usuarioService.alterarStatus(id, true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping(value = "/nome")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(usuarioService.findByNomeContainingIgnoreCase(nome));
    }

    @GetMapping(value = "/email")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_MASTER')")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping(value = "/status")
    @PreAuthorize("hasAnyAuthority('SCOPE_MASTER', 'SCOPE_ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorStatus(@RequestParam Boolean status) {
        return ResponseEntity.ok(usuarioService.findByStatus(status));
    }

    @GetMapping(value = "/roles")
    @PreAuthorize("hasAuthority('SCOPE_MASTER')")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorRole(@RequestParam("roles") String roleNome) {
        return ResponseEntity.ok(usuarioService.findByRoleUsuario(roleNome));
    }
}
