package com.alura.foro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String mensaje;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private Boolean solucion = false;

    private Boolean activo = true;
}