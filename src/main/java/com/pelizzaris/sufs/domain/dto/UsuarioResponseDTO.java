package com.pelizzaris.sufs.domain.dto;

import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nomeUsuario,
        String emailUsuario,
        Boolean statusUsuario
) {}
