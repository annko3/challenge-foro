package com.alura.foro.repository;

import com.alura.foro.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findAllByOrderByFechaCreacionDesc();
}