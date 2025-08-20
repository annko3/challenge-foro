// TopicoDto.java
package com.alura.foro.dto;

import com.alura.foro.entity.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicoDto {
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensaje;

    private LocalDateTime fechaCreacion;

    private StatusTopico status;

    @NotNull
    private Long autorId;

    @NotNull
    private Long cursoId;

    private List<RespuestaDto> respuestas;
}

