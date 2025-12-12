package com.pelizzaris.sufs.domain.dto;

public record UsuarioResponseDTO(
        Integer id,
        String nomeUsuario,
        String emailUsuario,
        Boolean statusUsuario
) {}
