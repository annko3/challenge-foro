package com.alura.foro.dto;

public record RegistroRequest(
        String nombre,
        String email,
        String password
) {}