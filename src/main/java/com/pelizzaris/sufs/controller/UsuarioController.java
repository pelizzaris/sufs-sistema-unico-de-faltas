package com.pelizzaris.sufs.controller;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.domain.model.util.Roles;
import com.pelizzaris.sufs.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO registrarUsuario(@RequestBody @Valid UsuarioCreateDTO dto) {
        return usuarioService.registrarUsuario(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable UUID id, @RequestBody @Valid UsuarioUpdateDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable UUID id) {
        usuarioService.alterarStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> reativar(@PathVariable UUID id) {
        usuarioService.alterarStatus(id, true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping(value = "/nome")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(usuarioService.findByNomeContainingIgnoreCase(nome));
    }

    @GetMapping(value = "/email")
    public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.findByEmailUsuario(email));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorStatus(@RequestParam Boolean status) {
        return ResponseEntity.ok(usuarioService.findByStatusUsuario(status));
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarPorRole(@RequestParam Roles roles) {
        return ResponseEntity.ok(usuarioService.findByRoleUsuario(roles));
    }
}
