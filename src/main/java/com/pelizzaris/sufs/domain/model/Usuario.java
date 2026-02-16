package com.pelizzaris.sufs.domain.model;

import com.pelizzaris.sufs.domain.model.util.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_usuario", length = 150, nullable = false)
    private String nomeUsuario;

    @Column(name = "email_usuario", unique = true, length = 150, nullable = false)
    private String emailUsuario;

    @Column(name = "status_usuario", nullable = false)
    private Boolean statusUsuario;

    //private Roles roles;
}
