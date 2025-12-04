package com.pelizzaris.sufs.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_falta")
public class Falta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    @NotNull
    @NotBlank(message = "É necessário registrar da data da falta!")
    private LocalDate dataFalta;
    @NotNull
    private LocalDateTime dataRegistro;
    private Integer usuarioID;
    private Integer alunoID;
}
