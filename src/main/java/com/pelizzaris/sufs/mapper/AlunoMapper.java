package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.AlunoCreateDTO;
import com.pelizzaris.sufs.domain.dto.AlunoResponseDTO;
import com.pelizzaris.sufs.domain.dto.AlunoUpdateDTO;
import com.pelizzaris.sufs.domain.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statusAluno", constant = "true")
    Aluno toEntity(AlunoCreateDTO alunoCreateDTO);

    AlunoResponseDTO toResponseDTO(Aluno aluno);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(AlunoUpdateDTO alunoUpdateDTO, @MappingTarget Aluno aluno);
}