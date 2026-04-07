package com.pelizzaris.sufs.repository;

import com.pelizzaris.sufs.domain.model.Falta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface FaltaRepository extends JpaRepository<Falta, Long>, JpaSpecificationExecutor<Falta> {

    /*List<Falta> findByDataFalta(LocalDate dataFalta);

     List<Falta> findByUsuarioId (UUID usuarioId);*/
}
