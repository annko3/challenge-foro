package com.alura.foro.service;


import com.alura.foro.dto.TopicoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TopicoService {
    List<TopicoDto> listarTopicos();
    Page<TopicoDto> listarTopicos(Pageable pageable);
    Optional<TopicoDto> obtenerTopicoPorId(Long id);
    TopicoDto crearTopico(TopicoDto topicoDto);
    Optional<TopicoDto> actualizarTopico(Long id, TopicoDto topicoDto);
    boolean eliminarTopico(Long id);
    List<TopicoDto> listarTopicosPorCurso(String cursoNombre);
    List<TopicoDto> listarTopicosPorCursoYYear(String cursoNombre, int year);
    boolean existeTopicoConMismoTituloYMensaje(String titulo, String mensaje);
    boolean existeTopicoConMismoTituloYMensajeYIdDiferente(String titulo, String mensaje, Long id);
}
