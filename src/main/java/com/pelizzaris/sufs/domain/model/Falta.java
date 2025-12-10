package com.pelizzaris.sufs.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_falta")
public class Falta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "A data da falta é obrigatória!")
    /*CONSTRUIR VALIDADOR DE DATA RETROATIVA*/
    private LocalDate dataFalta;
    @NotNull
    @PastOrPresent
    private LocalDateTime dataRegistro;
    @Size(max = 255)
    private String observacaoFalta;
    private Integer usuarioId;
    private Integer alunoId;
}
