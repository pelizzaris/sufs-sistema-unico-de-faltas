package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Aluno;
import com.pelizzaris.sufs.domain.model.Falta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaltaRepository extends JpaRepository<Falta, Long> {

    List<Falta> findByDataRegistro (String dataRegistro);

     List<Falta> findByUsuarioId (Integer usuarioId);
}
