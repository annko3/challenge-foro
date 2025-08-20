package com.alura.foro.repository;


import com.alura.foro.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByActivoTrue();

    Optional<Topico> findByIdAndActivoTrue(Long id);

    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :cursoNombre AND t.activo = true")
    List<Topico> findByCursoNombre(@Param("cursoNombre") String cursoNombre);

    @Query("SELECT t FROM Topico t WHERE t.activo = true ORDER BY t.fechaCreacion ASC")
    Page<Topico> findAllActivos(Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE YEAR(t.fechaCreacion) = :year AND t.curso.nombre = :cursoNombre AND t.activo = true")
    List<Topico> findByCursoAndYear(@Param("cursoNombre") String cursoNombre, @Param("year") int year);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);
}