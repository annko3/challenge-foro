package com.alura.foro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaDto {
    private Long id;
    private String mensaje;
    private Long autorId;
    private String autorNombre;
    private LocalDateTime fechaCreacion;
    private Boolean solucion;
}
