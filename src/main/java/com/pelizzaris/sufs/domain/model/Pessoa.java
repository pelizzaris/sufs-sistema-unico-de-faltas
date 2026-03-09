package com.pelizzaris.sufs.domain.model;

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
@MappedSuperclass
public abstract class Pessoa {

    @Id
    @Column(name = "pessoa_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_pessoa", length = 150, nullable = false)
    private String nome;

    @Column(name = "email_pessoa", unique = true, length = 150, nullable = false)
    private String email;

    @Column(name = "status_pessoa", nullable = false)
    private Boolean status = true;
}
