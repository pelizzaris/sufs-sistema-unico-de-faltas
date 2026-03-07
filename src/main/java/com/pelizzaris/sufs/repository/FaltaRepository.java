package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Falta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FaltaRepository extends JpaRepository<Falta, Long> {

    List<Falta> findByDataFalta(LocalDate dataFalta);

     List<Falta> findByUsuarioId (UUID usuarioId);
}
