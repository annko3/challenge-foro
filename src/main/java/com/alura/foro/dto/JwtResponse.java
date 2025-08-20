package com.alura.foro.dto;

public record JwtResponse(
        String token,
        String tipo
) {
    // Constructor que establece "Bearer" como valor por defecto
    public JwtResponse(String token) {
        this(token, "Bearer");
    }
}