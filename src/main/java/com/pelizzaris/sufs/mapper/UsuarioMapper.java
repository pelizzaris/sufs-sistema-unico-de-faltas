package com.pelizzaris.sufs.mapper;

import com.pelizzaris.sufs.domain.dto.UsuarioCreateDTO;
import com.pelizzaris.sufs.domain.dto.UsuarioResponseDTO;
import com.pelizzaris.sufs.domain.dto.UsuarioUpdateDTO;
import com.pelizzaris.sufs.domain.model.Usuario;
import com.pelizzaris.sufs.domain.model.util.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role")
    Usuario
    toEntity(UsuarioCreateDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UsuarioUpdateDTO usuarioUpdateDTO, @MappingTarget Usuario usuario);

    default Set<Roles> map(Roles.Values value) {
        if (value == null) return null;

        Roles entity = new Roles();
        entity.setRoleId(value.getRoleId());
        entity.setNome(value.name());

        return Set.of(entity);
    }

    default Roles.Values map(Set<Roles> value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        String nome = value.iterator().next().getNome().toUpperCase();
        return Roles.Values.valueOf(nome);
    }
}
