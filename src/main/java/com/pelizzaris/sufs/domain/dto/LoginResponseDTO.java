package com.pelizzaris.sufs.domain.dto;

public record LoginResponseDTO(
        String accessToken,
        Long expiresIn
) {}
