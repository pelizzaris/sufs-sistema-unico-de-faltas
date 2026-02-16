package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.FaltaCreateDTO;
import com.pelizzaris.sufs.domain.dto.FaltaResponseDTO;
import com.pelizzaris.sufs.domain.dto.RelatorioFaltaDTO;
import com.pelizzaris.sufs.domain.model.Falta;
import com.pelizzaris.sufs.domain.model.FaltaAluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FaltaMapper {

    // --- Mapeamento da FALTA (Cabeçalho) ---
    @Mapping(target = "alunosFaltosos", ignore = true) // A lista a gente preenche no Service
    Falta toEntity(FaltaCreateDTO dto);

    FaltaResponseDTO toResponseDTO(Falta entity);

    // --- Mapeamento para RELATÓRIOS (FaltaAluno) ---

    // Transforma a linha da tabela 'FaltaAluno' em um DTO simples para exibir na tela
    @Mapping(target = "dataFalta", source = "falta.dataFalta")
    @Mapping(target = "observacaoFalta", source = "falta.observacaoFalta")
    @Mapping(target = "nomeAluno", source = "aluno.nomeAluno")
    RelatorioFaltaDTO relatorioFalta(FaltaAluno entity);
}
