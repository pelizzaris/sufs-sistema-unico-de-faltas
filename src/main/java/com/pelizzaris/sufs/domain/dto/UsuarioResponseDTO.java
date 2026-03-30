package com.pelizzaris.sufs.domain.dto;

import com.pelizzaris.sufs.domain.model.util.Roles;

import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        Boolean status,
        Roles.Values role
) {}
