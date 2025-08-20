package com.alura.foro.dto;

public record TopicoRequest(
        String titulo,
        String mensaje,
        Long cursoId
) {}