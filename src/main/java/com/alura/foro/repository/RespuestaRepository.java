package com.alura.foro.repository;

import com.alura.foro.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByTopicoIdOrderByFechaCreacionAsc(Long topicoId);
}