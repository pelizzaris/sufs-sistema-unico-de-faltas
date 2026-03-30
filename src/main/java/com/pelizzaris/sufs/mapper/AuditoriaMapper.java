package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.Auditoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditoriaMapper {

    @Mapping(target = "usuario", source = "usuario.id")
    AuditoriaResponseDTO toResponseDTO(Auditoria auditoria);
}
