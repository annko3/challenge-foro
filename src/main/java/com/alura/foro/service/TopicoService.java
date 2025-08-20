package com.alura.foro.service;

import com.alura.foro.dto.TopicoRequest;
import com.alura.foro.dto.TopicoResponse;
import com.alura.foro.model.Curso;
import com.alura.foro.model.Topico;
import com.alura.foro.model.Usuario;
import com.alura.foro.repository.CursoRepository;
import com.alura.foro.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<TopicoResponse> listarTopicos() {
        return topicoRepository.findAllByOrderByFechaCreacionDesc()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public TopicoResponse crearTopico(TopicoRequest request, Usuario autor) {
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Topico topico = new Topico(
                request.titulo(),
                request.mensaje(),
                autor,
                curso
        );

        Topico savedTopico = topicoRepository.save(topico);
        return convertToResponse(savedTopico);
    }

    public void eliminarTopico(Long id, Usuario usuario) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        if (!topico.getAutor().getId().equals(usuario.getId())) {
            throw new RuntimeException("No autorizado para eliminar este tópico");
        }

        topicoRepository.delete(topico);
    }

    private TopicoResponse convertToResponse(Topico topico) {
        return new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus().toString(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}