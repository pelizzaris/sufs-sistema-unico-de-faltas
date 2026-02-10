package com.pelizzaris.sufs.domain.model;

import com.pelizzaris.sufs.domain.model.util.AcaoAuditoria;
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
    private Long id;

    @NotNull
    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @NotNull
    @PastOrPresent
    @Column(name = "acao_realizada", nullable = false)
    @Enumerated(EnumType.STRING)
    private AcaoAuditoria acaoRealizada;

    @Column(name = "falta_id", nullable = false)
    private Integer faltaId;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuaroId;
}
