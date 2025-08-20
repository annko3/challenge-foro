package com.alura.foro.model;

public enum StatusTopico {
    NO_RESPONDIDO,
    NO_SOLUCIONADO,
    SOLUCIONADO,
    CERRADO;

    @Override
    public String toString() {
        return this.name(); // Esto devuelve el nombre del valor del enum como String
    }
}