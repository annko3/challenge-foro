package com.alura.foro.controller;

import com.alura.foro.dto.TopicoDto;
import com.alura.foro.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public ResponseEntity<List<TopicoDto>> listarTopicos() {
        return ResponseEntity.ok(topicoService.listarTopicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDto> obtenerTopico(@PathVariable Long id) {
        Optional<TopicoDto> topico = topicoService.obtenerTopicoPorId(id);
        return topico.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TopicoDto> crearTopico(@RequestBody @Valid TopicoDto topicoDto) {
        TopicoDto nuevoTopico = topicoService.crearTopico(topicoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> actualizarTopico(@PathVariable Long id,
                                                      @RequestBody @Valid TopicoDto topicoDto) {
        Optional<TopicoDto> topicoActualizado = topicoService.actualizarTopico(id, topicoDto);
        return topicoActualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        if (topicoService.eliminarTopico(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/curso/{nombreCurso}")
    public ResponseEntity<List<TopicoDto>> listarTopicosPorCurso(@PathVariable String nombreCurso) {
        return ResponseEntity.ok(topicoService.listarTopicosPorCurso(nombreCurso));
    }
}