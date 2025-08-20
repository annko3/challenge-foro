package com.alura.foro.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    private Boolean solucion = false;

    // Constructores, getters y setters
    public Respuesta() {}

    public Respuesta(String mensaje, Usuario autor, Topico topico) {
        this.mensaje = mensaje;
        this.autor = autor;
        this.topico = topico;
    }

    // Getters y setters
    // ...
}