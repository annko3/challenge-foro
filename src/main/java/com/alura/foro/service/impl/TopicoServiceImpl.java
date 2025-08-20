package com.alura.foro.service.impl;


import com.alura.foro.dto.RespuestaDto;
import com.alura.foro.dto.TopicoDto;
import com.alura.foro.entity.*;
import com.alura.foro.repository.CursoRepository;
import com.alura.foro.repository.TopicoRepository;
import com.alura.foro.repository.UsuarioRepository;
import com.alura.foro.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoServiceImpl implements TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<TopicoDto> listarTopicos() {
        return topicoRepository.findByActivoTrue().stream()
                .map(this::convertirToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TopicoDto> listarTopicos(Pageable pageable) {
        return topicoRepository.findAllActivos(pageable)
                .map(this::convertirToDto);
    }

    @Override
    public Optional<TopicoDto> obtenerTopicoPorId(Long id) {
        return topicoRepository.findByIdAndActivoTrue(id)
                .map(this::convertirToDto);
    }

    @Override
    public TopicoDto crearTopico(TopicoDto topicoDto) {
        Usuario autor = usuarioRepository.findById(topicoDto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Curso curso = cursoRepository.findById(topicoDto.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Verificar si ya existe un tópico con el mismo título y mensaje
        if (topicoRepository.existsByTituloAndMensaje(topicoDto.getTitulo(), topicoDto.getMensaje())) {
            throw new RuntimeException("Ya existe un tópico con el mismo título y mensaje");
        }

        Topico topico = new Topico();
        topico.setTitulo(topicoDto.getTitulo());
        topico.setMensaje(topicoDto.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);

        Topico topicoGuardado = topicoRepository.save(topico);
        return convertirToDto(topicoGuardado);
    }

    @Override
    public Optional<TopicoDto> actualizarTopico(Long id, TopicoDto topicoDto) {
        return topicoRepository.findByIdAndActivoTrue(id)
                .map(topico -> {
                    // Verificar si ya existe otro tópico con el mismo título y mensaje
                    if (topicoRepository.existsByTituloAndMensajeAndIdNot(topicoDto.getTitulo(), topicoDto.getMensaje(), id)) {
                        throw new RuntimeException("Ya existe otro tópico con el mismo título y mensaje");
                    }

                    topico.setTitulo(topicoDto.getTitulo());
                    topico.setMensaje(topicoDto.getMensaje());

                    if (!topico.getCurso().getId().equals(topicoDto.getCursoId())) {
                        Curso nuevoCurso = cursoRepository.findById(topicoDto.getCursoId())
                                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
                        topico.setCurso(nuevoCurso);
                    }

                    Topico topicoActualizado = topicoRepository.save(topico);
                    return convertirToDto(topicoActualizado);
                });
    }

    @Override
    public boolean eliminarTopico(Long id) {
        return topicoRepository.findByIdAndActivoTrue(id)
                .map(topico -> {
                    topico.setActivo(false);
                    topicoRepository.save(topico);
                    return true;
                }).orElse(false);
    }

    @Override
    public List<TopicoDto> listarTopicosPorCurso(String cursoNombre) {
        return topicoRepository.findByCursoNombre(cursoNombre).stream()
                .map(this::convertirToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TopicoDto> listarTopicosPorCursoYYear(String cursoNombre, int year) {
        return topicoRepository.findByCursoAndYear(cursoNombre, year).stream()
                .map(this::convertirToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeTopicoConMismoTituloYMensaje(String titulo, String mensaje) {
        return topicoRepository.existsByTituloAndMensaje(titulo, mensaje);
    }

    @Override
    public boolean existeTopicoConMismoTituloYMensajeYIdDiferente(String titulo, String mensaje, Long id) {
        return topicoRepository.existsByTituloAndMensajeAndIdNot(titulo, mensaje, id);
    }

    private TopicoDto convertirToDto(Topico topico) {
        TopicoDto dto = new TopicoDto();
        dto.setId(topico.getId());
        dto.setTitulo(topico.getTitulo());
        dto.setMensaje(topico.getMensaje());
        dto.setFechaCreacion(topico.getFechaCreacion());
        dto.setStatus(topico.getStatus());
        dto.setAutorId(topico.getAutor().getId());
        dto.setCursoId(topico.getCurso().getId());
        dto.setAutorNombre(topico.getAutor().getNombre());
        dto.setCursoNombre(topico.getCurso().getNombre());

        if (topico.getRespuestas() != null) {
            dto.setRespuestas(topico.getRespuestas().stream()
                    .filter(Respuesta::getActivo)
                    .map(respuesta -> {
                        RespuestaDto respuestaDto = new RespuestaDto();
                        respuestaDto.setId(respuesta.getId());
                        respuestaDto.setMensaje(respuesta.getMensaje());
                        respuestaDto.setAutorId(respuesta.getAutor().getId());
                        respuestaDto.setAutorNombre(respuesta.getAutor().getNombre());
                        respuestaDto.setFechaCreacion(respuesta.getFechaCreacion());
                        respuestaDto.setSolucion(respuesta.getSolucion());
                        return respuestaDto;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}