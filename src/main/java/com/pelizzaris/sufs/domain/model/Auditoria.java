package com.pelizzaris.sufs.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_auditoria")
public class Auditoria {


    /*REVISAR E RECRIAR FUNCIONALIDADES*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private LocalDateTime dataRegistro;
    @NotNull
    @PastOrPresent
    private String acaoRealizada;
    private Integer faltaId;
    private Integer usuaroId;
}
