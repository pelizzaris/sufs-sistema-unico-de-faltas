package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.TurmaCreateDTO;
import com.pelizzaris.sufs.domain.dto.TurmaResponseDTO;
import com.pelizzaris.sufs.domain.dto.TurmaUpdateDTO;
import com.pelizzaris.sufs.domain.model.Turma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TurmaMapper {

    @Mapping(target = "id", ignore = true)
    Turma toEntity(TurmaCreateDTO dto);

    TurmaResponseDTO toResponseDTO(Turma turma);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TurmaUpdateDTO turmaUpdateDTO, @MappingTarget Turma turma);
}
