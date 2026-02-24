package com.pelizzaris.sufs.domain.dto;

import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        Boolean status
) {}
