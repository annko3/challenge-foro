package com.alura.foro.controller;

import com.alura.foro.dto.TopicoRequest;
import com.alura.foro.dto.TopicoResponse;
import com.alura.foro.model.Usuario;
import com.alura.foro.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public ResponseEntity<List<TopicoResponse>> listarTopicos() {
        List<TopicoResponse> topicos = topicoService.listarTopicos();
        return ResponseEntity.ok(topicos);
    }

    @PostMapping
    public ResponseEntity<TopicoResponse> crearTopico(
            @Valid @RequestBody TopicoRequest topicoRequest,
            @AuthenticationPrincipal Usuario usuario) {

        TopicoResponse topico = topicoService.crearTopico(topicoRequest, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario) {

        topicoService.eliminarTopico(id, usuario);
        return ResponseEntity.ok().build();
    }
}