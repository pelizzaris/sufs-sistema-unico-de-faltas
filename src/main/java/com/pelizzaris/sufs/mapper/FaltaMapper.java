package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.*;
import com.pelizzaris.sufs.domain.model.Falta;
import com.pelizzaris.sufs.domain.model.FaltaAluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FaltaMapper {

    @Mapping(target = "alunosFaltosos", ignore = true)
    Falta toEntity(FaltaCreateDTO dto);

    FaltaResponseDTO toResponseDTO(Falta falta);

    @Mapping(target = "dataFalta", source = "falta.dataFalta")
    @Mapping(target = "observacaoFalta", source = "falta.observacaoFalta")
    @Mapping(target = "nome", source = "aluno.nome")
    RelatorioFaltaAlunoResponseDTO relatorioFalta(FaltaAluno entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(FaltaUpdateDTO faltaUpdateDTO, @MappingTarget Falta falta);
}
