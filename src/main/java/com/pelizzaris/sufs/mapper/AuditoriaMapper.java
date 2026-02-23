package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.AuditoriaResponseDTO;
import com.pelizzaris.sufs.domain.model.Auditoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditoriaMapper {

    AuditoriaResponseDTO toResponseDTO(Auditoria auditoria);
}
