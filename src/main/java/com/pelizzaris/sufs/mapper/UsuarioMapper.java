package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.UsuarioCreateDTO;
import com.pelizzaris.sufs.domain.dto.UsuarioResponseDTO;
import com.pelizzaris.sufs.domain.dto.UsuarioUpdateDTO;
import com.pelizzaris.sufs.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role")
    Usuario toEntity(UsuarioCreateDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UsuarioUpdateDTO usuarioUpdateDTO, @MappingTarget Usuario usuario);
}
