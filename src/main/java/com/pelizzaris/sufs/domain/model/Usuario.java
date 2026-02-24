package com.pelizzaris.sufs.domain.model;

import com.pelizzaris.sufs.domain.model.util.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario extends Pessoa{

    @Enumerated(EnumType.STRING)
    @Column(name = "role_usuario", nullable = false)
    private Roles role;
}
